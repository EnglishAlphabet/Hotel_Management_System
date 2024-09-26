 @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        openPDFModal.setOnAction(e->openPdfModal());

    }
    String dest = "/Users/thantzinlin/Desktop/invoice.pdf"; //Change destination

    private void openPdfModal() {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);

        ImageView pdfImageView = new ImageView();

        try {

            ByteArrayOutputStream pdfInMemory = ByteInvoiceGen.createInvoice();
            PDDocument document = Loader.loadPDF(pdfInMemory.toByteArray());

            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage bufferedImage = renderer.renderImageWithDPI(0,200); // 0 -> first page


            Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);

            pdfImageView.setImage(fxImage);
            pdfImageView.setFitWidth(750);
            pdfImageView.setPreserveRatio(true);

            document.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Button saveBtn = new Button("save");

        saveBtn.setOnAction(e->{
            try(FileOutputStream fos = new FileOutputStream(dest)){
                ByteArrayOutputStream pdfInMemory = ByteInvoiceGen.createInvoice();
                pdfInMemory.writeTo(fos);
            }catch (IOException f){
                f.printStackTrace();
            }

        });

        VBox modalContent = new VBox(saveBtn,pdfImageView);
        Scene modalScene = new Scene(modalContent, 750, 600);
        modalStage.setScene(modalScene);
        modalStage.setTitle("Invoice PDF Viewer");
        modalStage.show();
    }
}
