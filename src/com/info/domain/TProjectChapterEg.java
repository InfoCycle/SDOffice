package com.info.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TProjectChapterEg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ProjectChapterEg", schema = "dbo", catalog = "SDOffice")
public class TProjectChapterEg implements java.io.Serializable {

    // Fields

    private Integer FId;
    private Integer FSealId;
    private String FSealName;
    private byte[] FPrintPictures;
    private String FRecipientsPeople;
    private String FRecipientsDate;
    private String FReturnDate;
    private byte[] FProcessingImages;
    private String FHandleMan;
    private String FHandleTime;
    private String FNote;
    private String FPicturesType;
    private String FImageType;

    // Constructors

    /** default constructor */
    public TProjectChapterEg() {
    }

    /** minimal constructor */
    public TProjectChapterEg(Integer FId) {
	this.FId = FId;
    }

    /** full constructor */
    public TProjectChapterEg(Integer FId, Integer FSealId, String FSealName,
	    byte[] FPrintPictures, String FRecipientsPeople,
	    String FRecipientsDate, String FReturnDate,
	    byte[] FProcessingImages, String FHandleMan, String FHandleTime,
	    String FNote, String FPicturesType, String FImageType) {
	this.FId = FId;
	this.FSealId = FSealId;
	this.FSealName = FSealName;
	this.FPrintPictures = FPrintPictures;
	this.FRecipientsPeople = FRecipientsPeople;
	this.FRecipientsDate = FRecipientsDate;
	this.FReturnDate = FReturnDate;
	this.FProcessingImages = FProcessingImages;
	this.FHandleMan = FHandleMan;
	this.FHandleTime = FHandleTime;
	this.FNote = FNote;
	this.FPicturesType = FPicturesType;
	this.FImageType = FImageType;
    }

    // Property accessors
    @Id
    @Column(name = "F_Id", unique = true, nullable = false)
    public Integer getFId() {
	return this.FId;
    }

    public void setFId(Integer FId) {
	this.FId = FId;
    }

    @Column(name = "F_SealId")
    public Integer getFSealId() {
	return this.FSealId;
    }

    public void setFSealId(Integer FSealId) {
	this.FSealId = FSealId;
    }

    @Column(name = "F_SealName", length = 200)
    public String getFSealName() {
	return this.FSealName;
    }

    public void setFSealName(String FSealName) {
	this.FSealName = FSealName;
    }

    @Column(name = "F_PrintPictures")
    public byte[] getFPrintPictures() {
	return this.FPrintPictures;
    }

    public void setFPrintPictures(byte[] FPrintPictures) {
	this.FPrintPictures = FPrintPictures;
    }

    @Column(name = "F_RecipientsPeople", length = 30)
    public String getFRecipientsPeople() {
	return this.FRecipientsPeople;
    }

    public void setFRecipientsPeople(String FRecipientsPeople) {
	this.FRecipientsPeople = FRecipientsPeople;
    }

    @Column(name = "F_RecipientsDate", length = 30)
    public String getFRecipientsDate() {
	return this.FRecipientsDate;
    }

    public void setFRecipientsDate(String FRecipientsDate) {
	this.FRecipientsDate = FRecipientsDate;
    }

    @Column(name = "F_ReturnDate", length = 30)
    public String getFReturnDate() {
	return this.FReturnDate;
    }

    public void setFReturnDate(String FReturnDate) {
	this.FReturnDate = FReturnDate;
    }

    @Column(name = "F_ProcessingImages")
    public byte[] getFProcessingImages() {
	return this.FProcessingImages;
    }

    public void setFProcessingImages(byte[] FProcessingImages) {
	this.FProcessingImages = FProcessingImages;
    }

    @Column(name = "F_HandleMan", length = 30)
    public String getFHandleMan() {
	return this.FHandleMan;
    }

    public void setFHandleMan(String FHandleMan) {
	this.FHandleMan = FHandleMan;
    }

    @Column(name = "F_HandleTime", length = 30)
    public String getFHandleTime() {
	return this.FHandleTime;
    }

    public void setFHandleTime(String FHandleTime) {
	this.FHandleTime = FHandleTime;
    }

    @Column(name = "F_Note", length = 800)
    public String getFNote() {
	return this.FNote;
    }

    public void setFNote(String FNote) {
	this.FNote = FNote;
    }

    @Column(name = "F_PicturesType", length = 8)
    public String getFPicturesType() {
	return this.FPicturesType;
    }

    public void setFPicturesType(String FPicturesType) {
	this.FPicturesType = FPicturesType;
    }

    @Column(name = "F_ImageType", length = 8)
    public String getFImageType() {
	return this.FImageType;
    }

    public void setFImageType(String FImageType) {
	this.FImageType = FImageType;
    }

}