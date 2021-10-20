package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.model.DataSource;
import sample.model.Project;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProjectsWindowController {

    @FXML
    private TableColumn<ObservableList<Project>, String> nameColumn;

    @FXML
    private TableColumn<ObservableList<Project>, String> adressColumn;

    @FXML
    private TableColumn<ObservableList<Project>, String> manufacturerColumn;

    @FXML
    private TableColumn<ObservableList<Project>, String> modelColumn;

    @FXML
    private TableColumn<ObservableList<Project>, String> powerColumn;

    @FXML
    private TableColumn<ObservableList<Project>, String> priceColumn;

    @FXML
    private TableColumn<ObservableList<Project>, String> requiredPowerColumn;

    @FXML
    private TableView<Project> projectsTable;

    public void initialize() {
        Task<ObservableList<Project>> task = new GetAllProjects();
        projectsTable.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        adressColumn.setCellValueFactory(new PropertyValueFactory<>("adress"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        powerColumn.setCellValueFactory(new PropertyValueFactory<>("power"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        requiredPowerColumn.setCellValueFactory(new PropertyValueFactory<>("requiredPower"));
    }

    @FXML
    public void handleGoBackButton(ActionEvent event) throws IOException {
        Parent projectsParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startWindow.fxml")));
        Scene projectsScene = new Scene(projectsParent, 800, 650);
        Stage projectsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        projectsStage.hide();
        projectsStage.setScene(projectsScene);
        projectsStage.show();
    }

    @FXML
    public void handleDeleteButton() {
        Project project = projectsTable.getSelectionModel().getSelectedItem();
        if (project == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No project selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the project you want to delete");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete project");
        alert.setHeaderText("Delete project: " + project.getName() + ", " + project.getAdress() + ", " + project.getManufacturer() + ", "
                + project.getModel() + ", " + project.getPower() + " kW");
        alert.setContentText("Are you sure? Press OK to confirm, or Cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            if (deleteProject()) {
                Task<ObservableList<Project>> task = new GetAllProjects();
                projectsTable.itemsProperty().bind(task.valueProperty());
                new Thread(task).start();
            } else {
                System.out.println("Failed to delete project from database");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error while trying to delete project from database");
                errorAlert.showAndWait();
            }
        }
    }

    private boolean deleteProject() {
        String name = projectsTable.getSelectionModel().getSelectedItem().getName();
        String adress = projectsTable.getSelectionModel().getSelectedItem().getAdress();
        String manufacturer = projectsTable.getSelectionModel().getSelectedItem().getManufacturer();
        String model = projectsTable.getSelectionModel().getSelectedItem().getModel();
        double power = projectsTable.getSelectionModel().getSelectedItem().getPower();
        int heatpumpId = DataSource.getInstance().queryIdFromHeatpumps(manufacturer, model, power);
        int clientId = DataSource.getInstance().queryIdFromClients(name, adress, heatpumpId);
        return DataSource.getInstance().deleteFromClients(clientId);
    }

    // This method provides shortcuts from keyboard to deleting a project
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DELETE)) {
            handleDeleteButton();
        }
    }

    @FXML
    public void handlePrintPDFButton() {
        try {
            Document myPdfReport = new Document();
            PdfWriter.getInstance(myPdfReport, new FileOutputStream("EcoHeatProjects.pdf"));
            myPdfReport.open();
            PdfPTable myReportTable = new PdfPTable(7);

            PdfPCell tableCell;

            List<Project> projectList = DataSource.getInstance().queryClients();

            tableCell = new PdfPCell(new Phrase("Name"));
            myReportTable.addCell(tableCell);

            tableCell = new PdfPCell(new Phrase("Adress"));
            myReportTable.addCell(tableCell);

            tableCell = new PdfPCell(new Phrase("Manufacturer"));
            myReportTable.addCell(tableCell);

            tableCell = new PdfPCell(new Phrase("Model"));
            myReportTable.addCell(tableCell);

            tableCell = new PdfPCell(new Phrase("Power"));
            myReportTable.addCell(tableCell);

            tableCell = new PdfPCell(new Phrase("Price"));
            myReportTable.addCell(tableCell);

            tableCell = new PdfPCell(new Phrase("Required power"));
            myReportTable.addCell(tableCell);

            for (Project project : projectList) {
                String name = project.getName();
                tableCell = new PdfPCell(new Phrase(name));
                myReportTable.addCell(tableCell);

                String adress = project.getAdress();
                tableCell = new PdfPCell(new Phrase(adress));
                myReportTable.addCell(tableCell);

                String manufacturer = project.getManufacturer();
                tableCell = new PdfPCell(new Phrase(manufacturer));
                myReportTable.addCell(tableCell);

                String model = project.getModel();
                tableCell = new PdfPCell(new Phrase(model));
                myReportTable.addCell(tableCell);

                double power = project.getPower();
                tableCell = new PdfPCell(new Phrase(String.valueOf(power)));
                myReportTable.addCell(tableCell);

                double price = project.getPrice();
                tableCell = new PdfPCell(new Phrase(String.valueOf(price)));
                myReportTable.addCell(tableCell);

                double requiredPower = project.getRequiredPower();
                tableCell = new PdfPCell(new Phrase(String.valueOf(requiredPower)));
                myReportTable.addCell(tableCell);
            }
            myPdfReport.add(myReportTable);
            myPdfReport.close();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "PDF is in the folder that contains" +
                    " the application");
            alert.setHeaderText("PDF successfully created");
            alert.showAndWait();

        } catch (FileNotFoundException | DocumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error when trying to generate PDF" +
                    " application");
            alert.showAndWait();
            System.out.println("Error when trying to generate PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

class GetAllProjects extends Task<ObservableList<Project>> {

    @Override
    public ObservableList<Project> call() {
        return FXCollections.observableArrayList(DataSource.getInstance().queryClients());

    }
}
