package com.cpiinfo.iot.logdict.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ShuPF
 * @Date 2021-01-06 10:13
 */
public class MyRequestWrapper extends HttpServletRequestWrapper {

    private String charset;

    private MyInputStream ins;

    private Integer partsNum = 0;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public MyRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.ins = new MyInputStream(super.getInputStream());
        this.charset = request.getCharacterEncoding();
    }

    public String getBody() {
        if(ins == null){
            return "";
        }
        return new String(ins.getBuf(), 0, ins.pos, Charset.forName(charset));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.ins;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public Collection<Part> getParts() throws IOException,
            ServletException {
        Collection<Part> parts = super.getParts();
        partsNum++;
        if (parts != null && partsNum == 1) {
            Map<String, Object> params = new HashMap<>();
            for(Part part : parts){
                String fileName = part.getSubmittedFileName();
                if (StringUtils.isNotBlank(fileName)) { /* 不读取文件 */
                    params.put("file", fileName);
                    continue;
                }
                this.setMap(params, part);
            }
            String bufStr = JSON.toJSONString(params);
            byte[] buf = bufStr.getBytes();
            ins.cacheData(buf, 0, buf.length);
        }
        return parts;
    }

    public Part getPart(String name) throws IOException,
            ServletException {
        Part part = super.getPart(name);
        Map<String, Object> params = new HashMap<>();
        this.setMap(params, part);
        byte[] buf = JSON.toJSONBytes(params);
        ins.cacheData(buf, 0, buf.length);
        return part;
    }

    private void setMap(Map<String, Object> params, Part part) throws IOException {
        String name = part.getName();
        byte[] buf = ins.copyStream(part.getInputStream());
        String value = new String(buf, 0, ins.pos, Charset.forName(charset));
        params.put(name, value);
    }

    /**
     *  在读取业务流的同时，复制一份
     *  限制大小为 10MB
     *  1byte = 8bit； 1KB = 1024 byte； 1MB = 1024 KB；
     */
    static class MyInputStream extends ServletInputStream {
        static int MAX_LEN = 10 * 1024 * 1024;

        byte[] buf = new byte[MAX_LEN];
        int pos = 0;
        ServletInputStream in;
        protected MyInputStream(ServletInputStream in) {
            this.in = in;
        }

        public byte[] getBuf(){
            return buf;
        }

        /**
         *  读取业务流并复制
         */
        public int read() throws IOException {
            int ret = in.read();
            if(pos < MAX_LEN) {
                buf[pos++] = (byte)ret;
            }
            return ret;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int count = in.read(b, off, len);
            cacheData(b, off, count);
            return count;
        }

        @Override
        public long skip(long n) throws IOException {
            return in.skip(n);
        }

        @Override
        public int available() throws IOException {
            return in.available();
        }

        @Override
        public void close() throws IOException {
            in.close();
        }

        @Override
        public synchronized void mark(int readlimit) {
            in.mark(readlimit);
        }

        @Override
        public synchronized void reset() throws IOException {
            in.reset();
        }

        @Override
        public boolean markSupported() {
            return in.markSupported();
        }

        /**
         *  给 buf 赋值
         * @param b 原byte
         * @param off 开始位置
         * @param count 长度
         */
        private void cacheData(byte[] b, int off, int count) {
            if(pos < MAX_LEN) {
                System.arraycopy(b, off, buf, pos, Math.min(count, MAX_LEN - pos));
                pos += Math.min(count, MAX_LEN - pos);
            }
        }

        @Override
        public int read(byte[] b) throws IOException {
            int count = in.read(b);
            cacheData(b, 0, count);
            return count;
        }

        @Override
        public boolean isFinished() {
            return in.isFinished();
        }

        @Override
        public boolean isReady() {
            return in.isReady();
        }

        @Override
        public void setReadListener(ReadListener listener) {
            in.setReadListener(listener);
        }

        /**
         *  读取 Part 中的参数的value值
         */
        public byte[] copyStream(InputStream inputStream) throws IOException {
            int maxLen = 1024 * 1024;
            byte[] bytes = new byte[maxLen];
            pos = 0;
            while(pos < maxLen) {
                int val = inputStream.read();
                if(val == -1){
                    break;
                }
                bytes[pos++] = (byte)val;
            }

            return bytes;
        }
    }
}
