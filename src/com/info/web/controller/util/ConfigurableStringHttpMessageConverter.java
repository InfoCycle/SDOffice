package com.info.web.controller.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.util.FileCopyUtils;

public class ConfigurableStringHttpMessageConverter extends
        AbstractHttpMessageConverter<String> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public static Charset charsetOverride = null;

    private final List<Charset> availableCharsets;

    private boolean writeAcceptCharset = true;

    public ConfigurableStringHttpMessageConverter(Charset defaultCharset) {
        super();
        this.availableCharsets = new ArrayList<Charset>();
        this.availableCharsets.add(defaultCharset);
        charsetOverride = defaultCharset;
    }

    public ConfigurableStringHttpMessageConverter() {
        this(DEFAULT_CHARSET);
    }

    /**
     * Indicates whether the {@code Accept-Charset} should be written to any
     * outgoing request.
     * <p>
     * Default is {@code true}.
     */
    public void setWriteAcceptCharset(boolean writeAcceptCharset) {
        this.writeAcceptCharset = writeAcceptCharset;
    }

    /*
    @Override
    protected MediaType getDefaultContentType(String dumy) {
        return new MediaType("text", "plain", DEFAULT_CHARSET);
    }
    */

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    protected String readInternal(@SuppressWarnings("rawtypes") Class clazz,
            HttpInputMessage inputMessage) throws IOException {
        MediaType contentType = inputMessage.getHeaders().getContentType();
        Charset charset = contentType.getCharSet() != null ? contentType
                .getCharSet() : DEFAULT_CHARSET;
        return FileCopyUtils.copyToString(new InputStreamReader(inputMessage
                .getBody(), charset));
    }

    @Override
    protected Long getContentLength(String s, MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            Charset charset = contentType.getCharSet();
            try {
                return (long) s.getBytes(charset.name()).length;
            } catch (UnsupportedEncodingException ex) {
                // should not occur
                throw new InternalError(ex.getMessage());
            }
        } else {
            return null;
        }
    }

    @Override
    protected void writeInternal(String s, HttpOutputMessage outputMessage)
            throws IOException {
        if (writeAcceptCharset) {
            outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
        }
        MediaType contentType = outputMessage.getHeaders().getContentType();
        Charset charset = contentType.getCharSet() != null ? contentType
                .getCharSet() : (charsetOverride != null ? charsetOverride
                : DEFAULT_CHARSET);
        FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(),
                charset));
    }

    /**
     * Return the list of supported {@link Charset}.
     *
     * <p>
     * By default, returns {@link Charset#availableCharsets()}. Can be
     * overridden in subclasses.
     *
     * @return the list of accepted charsets
     */
    protected List<Charset> getAcceptedCharsets() {
        return this.availableCharsets;
    }
   
    @Override
    protected boolean canWrite(MediaType mediaType) {
        return true;
    }
}