package com.info.web.controller.util;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class PhotoFile {
	private CommonsMultipartFile photos;
	 
    public CommonsMultipartFile getPhotos() {
        return photos;
    }
 
    public void setPhotos(CommonsMultipartFile photos) {
        this.photos = photos;
    }
}
