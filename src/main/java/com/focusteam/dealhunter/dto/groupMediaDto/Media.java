package com.focusteam.dealhunter.dto.groupMediaDto;

public class Media {
    private String fileName;
    private String fileDownloadUri;
    private String message;
    private String fileType;
    private long size;
    private long uploaded;

    public Media(String fileName, String message, String fileType, long size) {
        this.fileName = fileName;
        this.message = message;
        this.fileType = fileType;
        this.size = size;
    }

    public Media(String fileName, String fileDownloadUri, String message, String fileType, long size, long uploaded) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.message = message;
        this.fileType = fileType;
        this.size = size;
        this.uploaded = uploaded;
    }


    public Media() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getUploaded() {
        return uploaded;
    }

    public void setUploaded(long uploaded) {
        this.uploaded = uploaded;
    }

}
