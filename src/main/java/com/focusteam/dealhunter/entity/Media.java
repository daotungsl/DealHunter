package com.focusteam.dealhunter.entity;

public class Media {
    private String fileName;
    private String fileDownloadUri;
    private String fileUrl;
    private String fileType;
    private long size;
    private long uploaded;

    public Media(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public Media(String fileName, String fileDownloadUri, String fileUrl, String fileType, long size, long uploaded) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileUrl = fileUrl;
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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


    public static final class MediaBuilder {
        private String fileName;
        private String fileDownloadUri;
        private String fileUrl;
        private String fileType;
        private long size;
        private long uploaded;

        private MediaBuilder() {
        }

        public static MediaBuilder aMedia() {
            return new MediaBuilder();
        }

        public MediaBuilder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public MediaBuilder withFileDownloadUri(String fileDownloadUri) {
            this.fileDownloadUri = fileDownloadUri;
            return this;
        }

        public MediaBuilder withFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
            return this;
        }

        public MediaBuilder withFileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public MediaBuilder withSize(long size) {
            this.size = size;
            return this;
        }

        public MediaBuilder withUploaded(long uploaded) {
            this.uploaded = uploaded;
            return this;
        }

        public Media build() {
            Media media = new Media();
            media.setFileName(fileName);
            media.setFileDownloadUri(fileDownloadUri);
            media.setFileUrl(fileUrl);
            media.setFileType(fileType);
            media.setSize(size);
            media.setUploaded(uploaded);
            return media;
        }
    }
}
