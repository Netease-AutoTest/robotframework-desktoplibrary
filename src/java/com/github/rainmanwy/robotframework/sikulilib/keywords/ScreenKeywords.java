package com.github.rainmanwy.robotframework.sikulilib.keywords;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import org.robotframework.javalib.annotation.*;

import com.github.rainmanwy.robotframework.sikulilib.exceptions.TimeoutException;
import com.github.rainmanwy.robotframework.sikulilib.exceptions.ScreenOperationException;
import com.github.rainmanwy.robotframework.sikulilib.utils.CaptureFolder;

import org.sikuli.script.*;

/**
 * Created by Wang Yang on 2015/8/19.
 */

@RobotKeywords
public class ScreenKeywords {

    private static double DEFAULT_TIMEOUT = 5.0;
    private final Screen screen = new Screen();
    private double timeout;
    private Map<String, Match> highlightMap = new HashMap<String, Match>();

    public ScreenKeywords() {
        timeout = DEFAULT_TIMEOUT;
    }

    private Match wait(String image, String timeout) throws TimeoutException {
        try {
            Match match = screen.wait(image, Double.parseDouble(timeout));
            capture(match);
            return match;
        }
        catch(FindFailed e) {
            capture();
            throw new TimeoutException("Timeout happend, could not find "+image, e);
        }
    }

    private Match find(String image) {
        try {
            Match match = screen.find(image);
            capture(match);
            return match;
        } catch (FindFailed e) {
            System.out.println("Could not find " + image);
            return null;
        }
    }


    private void capture() {
        ScreenImage image = screen.capture();
        String imagePath = image.save(CaptureFolder.getInstance().getCaptureFolder());
        System.out.println("*DEBUG* Saved path: "+imagePath);
        File file = new File(imagePath);
        String fileName = file.getName();
        System.out.println("*HTML* <img src='" + CaptureFolder.getInstance().getSubFolder() + "/" + fileName + "'/>");
    }

    private void capture(Region region) {
        ScreenImage image = screen.capture(region);
        String imagePath = image.save(CaptureFolder.getInstance().getCaptureFolder());
        System.out.println("*DEBUG* Saved path: "+imagePath);
        File file = new File(imagePath);
        String fileName = file.getName();
        System.out.println("*HTML* <img src='" + CaptureFolder.getInstance().getSubFolder() + "/" + fileName + "'/>");
    }
    @RobotKeyword("截取整个屏幕")
    @ArgumentNames({})
    public void desktopCaptureScreen(){
        capture();
    }

    //设置类关键字
    @RobotKeyword("设置sikuli失效时间（单位：s）")
    @ArgumentNames({"timeout"})
    public String desktopSetTimeout(String timeout) {
        double oldTimeout = this.timeout;
        this.timeout = Double.parseDouble(timeout);
        return Double.toString(oldTimeout);
    }

    @RobotKeyword("设置使用图片路径")
    @ArgumentNames({"path"})
    public boolean desktopSetImagePath(String path) {
        return ImagePath.add(path);
    }

    @RobotKeyword("设置截图保存路径")
    @ArgumentNames({"path"})
    public void desktopSetCaptureFolder(String path) {
        CaptureFolder.getInstance().setCaptureFolder(path);
    }
    
    
    //鼠标动作关键字
    @RobotKeyword("单击图片，仅有一张图片时，点击该图片；有两张图片时，从第二张图片区域寻找第一张指定图片，第二张图片可为空")
    @ArgumentNames({"targetImage","areaImage="})
    public void desktopClick(String targetImage, String areaImage) throws Exception {      
        Match match = wait(areaImage, Double.toString(this.timeout));
        System.out.println(areaImage + " is found!");
        match.click(targetImage);
        capture(match.find(targetImage));        
    }

    @RobotKeywordOverload
    public void desktopClick(String targetImage) throws Exception {      
        wait(targetImage, Double.toString(this.timeout));
        try {
        screen.click(targetImage);
        }
        catch (FindFailed e) {
        capture();
        throw new ScreenOperationException("Click "+targetImage+" failed"+e.getMessage(), e);
        }
        
    }   
    
    @RobotKeyword("双击图片，仅有一张图片时，点击该图片；有两张图片时，从第二张图片区域寻找第一张指定图片，第二张图片可为空")
    @ArgumentNames({"targetImage","areaImage="})
    public void desktopDoubleClick(String targetImage, String areaImage) throws Exception{       
        Match match = wait(areaImage, Double.toString(this.timeout));
        System.out.println(areaImage + " is found!");
        match.doubleClick(targetImage);
        capture(match.find(targetImage));
    }   

    @RobotKeywordOverload
    public void desktopDoubleClick(String targetImage) throws Exception{      
        wait(targetImage, Double.toString(this.timeout));
        try {
        screen.doubleClick(targetImage);
        }
        catch (FindFailed e) {
        capture();
        throw new ScreenOperationException("Double click "+targetImage+" failed"+e.getMessage(), e);
        }
    }   
    

    @RobotKeyword("鼠标右键点击图片,仅有一张图片时，点击该图片；有两张图片时，从第二张图片区域寻找第一张指定图片，第二张图片可为空")
    @ArgumentNames({"targetImage","areaImage="})
    public void desktopRightClick(String targetImage, String areaImage) throws Exception {       
        Match match = wait(areaImage, Double.toString(this.timeout));
        System.out.println(areaImage + " is found!");
        match.rightClick(targetImage);
        capture(match.find(targetImage));  
    }

    @RobotKeywordOverload
    public void desktopRightClick(String targetImage) throws Exception {       
        wait(targetImage, Double.toString(this.timeout));
        try {
        screen.rightClick(targetImage);
        }
        catch (FindFailed e) {
        capture();
        throw new ScreenOperationException("Right click "+targetImage+" failed"+e.getMessage(), e);
        }
    }
    
    //查找/判断类关键字
    @RobotKeyword("等待当前屏幕出现指定图片")
    @ArgumentNames({"image", "timeout"})
    public void desktopWaitUntilScreenContain(String image, String timeout) throws TimeoutException {
        wait(image, timeout);
    }

    @RobotKeyword("当前屏幕应包含指定图片")
    @ArgumentNames({"targetImage","areaImage="})
    public void desktopScreenShouldContain(String targetImage, String areaImage) throws ScreenOperationException {
        Match area_match = find(areaImage);
        if (area_match == null) {
            throw new ScreenOperationException("Screen should contain "+areaImage);
        }
        else{
            Match target_match = find(targetImage);
            if(target_match == null){
                throw new ScreenOperationException("Screen should contain "+targetImage);
            }
        }
    }

    @RobotKeywordOverload
    public void desktopScreenShouldContain(String targetImage) throws ScreenOperationException {
        Match match = find(targetImage);
        if (match == null) {
            throw new ScreenOperationException("Screen should contain "+targetImage);
        }
    }


    @RobotKeyword("当前屏幕不应包含指定图片")
    @ArgumentNames({"targetImage","areaImage="})
    public void desktopScreenShouldNotContain(String targetImage, String areaImage) throws ScreenOperationException {
        Match area_match = find(areaImage);
        if (area_match != null) {
            throw new ScreenOperationException("Screen should not contain "+areaImage);
        }
        else{
            Match target_match = find(targetImage);
            if(target_match != null){
                throw new ScreenOperationException("Screen should not contain "+targetImage);
            }
        }
        
    }

    @RobotKeywordOverload
    public void desktopScreenShouldNotContain(String targetImage) throws ScreenOperationException {
        Match match = find(targetImage);
        if (match != null) {
            throw new ScreenOperationException("Screen should not contain "+targetImage);
        }
    }

    //键盘动作关键字
    @RobotKeyword("输入文本，图片可以为空，不支持中文输入")
    @ArgumentNames({"text","image="})
    public void desktopInputText(String text,String image) throws Exception {
        System.out.println("Input Text:");
        System.out.println(text);
        this.desktopClick(image);
        int result = screen.type(text);
        if (result == 0) {
            throw new ScreenOperationException("Input text failed");
        }
    }
    
    @RobotKeywordOverload
    public void desktopInputText(String text) throws Exception {
        System.out.println("Input Text:");
        System.out.println(text);
        int result = screen.type(text);
        if (result == 0) {
            throw new ScreenOperationException("Input text failed");
        }
    }

    @RobotKeyword("粘贴文本，图片可以为空，解决中文输入问题")
    @ArgumentNames({"text","image="})
    public void desktopPasteText(String text,String image) throws Exception {
        System.out.println("Paste Text:");
        System.out.println(text);
        this.desktopClick(image);
        int result = screen.paste(text);
        if (result == 0) {
            throw new ScreenOperationException("Paste text failed");
        }
    } 
    
    @RobotKeywordOverload 
    public void desktopPasteText(String text) throws Exception {
        System.out.println("Paste Text:");
        System.out.println(text);
        int result = screen.paste(text);
        if (result == 0) {
            throw new ScreenOperationException("Paste text failed");
        }
    }  

}
