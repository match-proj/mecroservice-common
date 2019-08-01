package com.match.common.icon.group;


import com.match.common.icon.IconServiceProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/6/21 9:13
 * @Version v1.0
 */
public class GroupIconServiceProvider implements IconServiceProvider<List<String>> {

    /** 图片格式：JPG */
    private static final String PICTRUE_FORMATE_JPG = "JPG";
    private static final int PIC_WIDTH = 400; // 这是画板的宽高
    private static final int PIC_HEIGHT = 400; // 这是画板的高度
//
//    public static void main(String[] args) throws IOException {
//        GroupIconServiceProvider provider = new GroupIconServiceProvider();
//        String destPath = "/opt/";
//        List<String> sourcePics = new ArrayList<>();
//        sourcePics.add("https://flyants.oss-cn-shanghai.aliyuncs.com/headimg/02a7ebddc4a94d04abeaec642394297b.jpg");
//        sourcePics.add("https://flyants.oss-cn-shanghai.aliyuncs.com/headimg/1f4b6b59608c41478885290199f90078.jpg");
//        sourcePics.add("https://flyants.oss-cn-shanghai.aliyuncs.com/headimg/72c28ff0e2ad4e06a09d02b40b877e48.jpg");
//        sourcePics.add("https://flyants.oss-cn-shanghai.aliyuncs.com/headimg/e75b706f133b421287ca579996bc030a.jpg");
//        sourcePics.add("https://flyants.oss-cn-shanghai.aliyuncs.com/headimg/e75b706f133b421287ca579996bc030a.jpg");
//        sourcePics.add("https://flyants.oss-cn-shanghai.aliyuncs.com/headimg/e75b706f133b421287ca579996bc030a.jpg");
//        sourcePics.add("https://flyants.oss-cn-shanghai.aliyuncs.com/headimg/e75b706f133b421287ca579996bc030a.jpg");
//        sourcePics.add("https://flyants.oss-cn-shanghai.aliyuncs.com/headimg/e75b706f133b421287ca579996bc030a.jpg");
//        sourcePics.add("https://flyants.oss-cn-shanghai.aliyuncs.com/headimg/e75b706f133b421287ca579996bc030a.jpg");
//        sourcePics.add("https://flyants.oss-cn-shanghai.aliyuncs.com/headimg/e75b706f133b421287ca579996bc030a.jpg");
//        for(int i = 1; i <= sourcePics.size(); i++){
//            provider.generate(sourcePics.subList(0, i), destPath + "out" + i + ".jpg");
//        }
//    }

    @Override
    public void generate(List<String> userAvatars, OutputStream out) throws IOException {
        if(userAvatars.size() > 9){
            userAvatars = userAvatars.subList(0,9);
        }
        int totalPicNum = userAvatars.size();
        PicInfo picInfo = getPicInfo(totalPicNum);
        List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
        // 压缩图片所有的图片生成尺寸
        for (int i = 0; i < totalPicNum; i++) {
            bufferedImages.add(resizeNetWorkImage(userAvatars.get(i), picInfo.getPerPicWith(), picInfo.getPerPicHeight(), true));
        }
        BufferedImage outImage = new BufferedImage(PIC_WIDTH, PIC_HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 生成画布
        Graphics graphics = outImage.getGraphics();
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.setBackground(new Color(231,231,231));
        graphics2d.clearRect(0, 0, PIC_WIDTH, PIC_HEIGHT);
        //开始将单个图片逐个画到画布上
        for (int picIndex = 0; picIndex < bufferedImages.size(); picIndex++) {
            if(totalPicNum == 2 || totalPicNum == 5 || totalPicNum == 6){
                //需要特殊处理，来让图片垂直居中
                specialDraw(bufferedImages.get(picIndex), picIndex, picInfo, graphics2d, totalPicNum);
            }else{
                //不需要特殊处理，按照正常的处理逻辑
                normalDraw(bufferedImages.get(picIndex), picIndex, picInfo, graphics2d);
            }
        }
        //将最终结果图片输出到指定文件
        ImageIO.write(outImage, PICTRUE_FORMATE_JPG, out);
    }


    /**
     * 生成群头像
     * @param userAvatars 用户头像
     * @throws IOException
     */
    public void generate(List<String> userAvatars, String destPath) throws IOException {
       generate(userAvatars,new FileOutputStream(new File(destPath)));
    }

    private void specialDraw(BufferedImage bufferedImage, int picIndex, PicInfo picInfo, Graphics2D graphics2d, int totalPicNum) {
        int xIndex = (picIndex % picInfo.getPicNumPerRow());
        int y = 0;
        if(totalPicNum == 2){
            y = PIC_HEIGHT / 4;
        }else if(totalPicNum == 5 || totalPicNum == 6){
            if(picIndex < 3){
                y = (PIC_HEIGHT / 2 - PIC_HEIGHT / 3);
            }else{
                y = PIC_HEIGHT / 2;
            }
        }
        graphics2d.drawImage(bufferedImage, xIndex * picInfo.getPerPicWith(), y, null);
    }

    private void normalDraw(BufferedImage bufferedImage, int picIndex, PicInfo picInfo, Graphics2D graphics2d) {
        int xIndex = (picIndex % picInfo.getPicNumPerRow());
        int yIndex = (picIndex / picInfo.getPicNumPerRow());
        graphics2d.drawImage(bufferedImage, xIndex * picInfo.getPerPicWith(), yIndex * picInfo.getPerPicHeight(), null);
    }

    /**
     * 图片缩放
     * @param bufferedImage 图片路径
     * @param width 宽度
     * @param height 高度
     * @param fillWhite 比例不对时是否需要补白
     */
    public BufferedImage resizeImage(BufferedImage bufferedImage, int width, int height, boolean fillWhite) {
        double ratio = 0; // 缩放比例
        Image newImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // 计算比例
        if ((bufferedImage.getHeight() > height) || (bufferedImage.getWidth() > width)) {
            if (bufferedImage.getHeight() > bufferedImage.getWidth()) {
                ratio = (new Integer(height)).doubleValue()/bufferedImage.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue()/bufferedImage.getWidth();
            }
            AffineTransformOp affineTransformOp = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            newImage = affineTransformOp.filter(bufferedImage, null);
        }
        if (fillWhite) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            if (width == newImage.getWidth(null)){
                g.drawImage(newImage, 0, (height - newImage.getHeight(null))/2, newImage.getWidth(null), newImage.getHeight(null), Color.white, null);
            }else{
                g.drawImage(newImage, (width - newImage.getWidth(null))/2, 0, newImage.getWidth(null), newImage.getHeight(null), Color.white, null);
            }
            g.dispose();
            newImage = image;
        }
        return (BufferedImage) newImage;
    }

    /**
     * 缩放本地图片
     * @param file
     * @param width
     * @param height
     * @param fillWhite
     * @return
     */
    public BufferedImage resizeLocalImage(File file, int width, int height, boolean fillWhite) {
        try {
            //从本地加载图片
            BufferedImage bufferedImage = ImageIO.read(file);
            return resizeImage(bufferedImage, width, height, fillWhite);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缩放网络图片
     * @param imageUrl
     * @param width
     * @param height
     * @param fillWhite
     * @return
     */
    public BufferedImage resizeNetWorkImage(String imageUrl, int width, int height, boolean fillWhite) {
        try {
            //从网络加载图片
            BufferedImage bufferedImage = ImageIO.read(new URL(imageUrl));
            return resizeImage(bufferedImage, width, height, fillWhite);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取群头像中每行/列显示的图片数
     * @param totalPicNum 图片数量
     * @return
     */
    private PicInfo getPicInfo(int totalPicNum){
        //每行显示图片数
        int picNumPerRow = 1;
        //每列显示图片数
        int picNumPerCol = 1;
        switch (totalPicNum) {
            case 1:
                picNumPerRow = 1;
                picNumPerCol = 1;
                break;
            case 2:
                picNumPerRow = 2;
                picNumPerCol = 1;
                break;
            case 3:
            case 4:
                picNumPerRow = 2;
                picNumPerCol = 2;
                break;
            case 5:
            case 6:
                picNumPerRow = 3;
                picNumPerCol = 2;
                break;
            case 7:
            case 8:
            case 9:
                picNumPerRow = 3;
                picNumPerCol = 3;
                break;
            default:
                picNumPerRow = 1;
                picNumPerCol = 1;
                break;
        }
        int perPicWith = PIC_WIDTH/picNumPerRow;
        int perPicHeight = PIC_HEIGHT/picNumPerCol;

        //图片有效宽/高
        int effectWithHeight = Math.min(perPicWith, perPicHeight);

        PicInfo picInfo = new PicInfo();
        //图片的宽高统一
        picInfo.setPerPicWith(effectWithHeight);
        picInfo.setPerPicHeight(effectWithHeight);
        picInfo.setPicNumPerRow(picNumPerRow);
        picInfo.setPicNumPerCol(picNumPerCol);
        return picInfo;
    }

    /**
     * 图片位置
     * @author tianchaohui
     */
    public static class PicLocation{
        //横坐标
        int xIndex = 0;
        //纵坐标
        int yIndex = 0;
        public int getxIndex() {
            return xIndex;
        }
        public void setxIndex(int xIndex) {
            this.xIndex = xIndex;
        }
        public int getyIndex() {
            return yIndex;
        }
        public void setyIndex(int yIndex) {
            this.yIndex = yIndex;
        }
    }

    /**
     * 填充图片信息
     * @author tianchaohui
     */
    public static class PicInfo{
        //每张图片宽度
        private int perPicWith;
        //每张图片高度
        private int perPicHeight;
        //每行显示图片数
        int picNumPerRow;
        //每列显示图片数
        int picNumPerCol;
        public int getPerPicWith() {
            return perPicWith;
        }
        public void setPerPicWith(int perPicWith) {
            this.perPicWith = perPicWith;
        }
        public int getPerPicHeight() {
            return perPicHeight;
        }
        public void setPerPicHeight(int perPicHeight) {
            this.perPicHeight = perPicHeight;
        }
        public int getPicNumPerRow() {
            return picNumPerRow;
        }
        public void setPicNumPerRow(int picNumPerRow) {
            this.picNumPerRow = picNumPerRow;
        }
        public int getPicNumPerCol() {
            return picNumPerCol;
        }
        public void setPicNumPerCol(int picNumPerCol) {
            this.picNumPerCol = picNumPerCol;
        }
        @Override
        public String toString() {
            return "PicInfo [perPicWith=" + perPicWith + ", perPicHeight=" + perPicHeight + ", picNumPerRow="
                    + picNumPerRow + ", picNumPerCol=" + picNumPerCol + "]";
        }
    }

}