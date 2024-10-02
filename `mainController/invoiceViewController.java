}  
 checkout.setOnAction(e->openPdfModal());

    }
    String dest = "/Users/thantzinlin/Desktop/invoice.pdf";

    private void openPdfModal() {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);

        ImageView pdfImageView = new ImageView();

        PDDocument document = loadPDF();
        if (document == null) return;
        try{
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage bufferedImage = renderer.renderImageWithDPI(0,300); // 0 -> first page

            Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);

            pdfImageView.setImage(fxImage);
            pdfImageView.setFitWidth(750);
            pdfImageView.setPreserveRatio(true);

        }catch (Exception e){
            e.printStackTrace();
        }

        Button saveBtn = new Button("save");
        Button printBtn = new Button("print");

        ComboBox<PrintService> printers = new ComboBox<>();
        PrintService[] pr = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService p : pr) {
            printers.getItems().add(p);
        }
        printers.getSelectionModel().selectFirst();

        HBox controlsBox = new HBox(saveBtn,printers,printBtn);

        saveBtn.setOnAction(e->{
            try(FileOutputStream fos = new FileOutputStream(dest)){
                ByteArrayOutputStream pdfInMemory = ByteInvoiceGen.createInvoice();
                pdfInMemory.writeTo(fos);
            }catch (IOException f){
                f.printStackTrace();
            }
        });

        printBtn.setOnAction(e->printPDF(document, printers.getSelectionModel().getSelectedItem()));

        VBox modalContent = new VBox(controlsBox,pdfImageView);
        Scene modalScene = new Scene(modalContent, 750, 600);
        modalStage.setScene(modalScene);
        modalStage.setTitle("Invoice PDF Viewer");
        modalStage.show();
    }
    private PDDocument loadPDF(){
        try {

            ByteArrayOutputStream pdfInMemory = ByteInvoiceGen.createInvoice();
            PDDocument document = Loader.loadPDF(pdfInMemory.toByteArray());
            return  document;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    private void printPDF(PDDocument document, PrintService printerChoice){

        PrinterJob job = PrinterJob.getPrinterJob();
        try{
            job.setPageable(new PDFPageable(document));
            job.setPrintService(printerChoice);
            job.print();
            document.close();
        }catch (Exception e){
            e.printStackTrace();
            job.cancel();
        }

    }
}
