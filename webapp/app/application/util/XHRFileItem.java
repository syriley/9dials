package application.util;

import org.apache.commons.fileupload.FileItem;

import java.io.*;

import static play.mvc.Http.Request.current;

/**
 * An implementation of FileItem to deal with XmlHttpRequest file uploads.
 */
public class XHRFileItem implements FileItem {

    private String fieldName;

    public XHRFileItem(String fieldName) {
        this.fieldName = fieldName;
    }

    public InputStream getInputStream() throws IOException {
        return current().body;
    }

    public String getContentType() {
        return current().contentType;
    }

    public String getName() {
        //String fileName = current().params.get(fieldName);
        String fileName = "testSound.ogg";
        if (fileName == null) {
            fileName = current().headers.get("x-file-name").value();
        }
        return fileName;
    }

    public boolean isInMemory() {
        return false;
    }

    public long getSize() {
        return 0;
    }

    public byte[] get() {
        return new byte[0];
    }

    public String getString(String s) throws UnsupportedEncodingException {
        return s;
    }

    public String getString() {
        return "";
    }

    public void write(File file) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        InputStream is = getInputStream();
        byte[] buf = new byte[64000];

        int read;
        while ((read = is.read(buf)) != -1) {
            fos.write(buf, 0, read);
        }
        fos.close();
    }

    public void delete() {

    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isFormField() {
        return false;
    }

    public void setFormField(boolean b) {

    }

    public OutputStream getOutputStream() throws IOException {
        return null;
    }
}