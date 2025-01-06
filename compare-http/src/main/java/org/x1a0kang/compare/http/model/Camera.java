package org.x1a0kang.compare.http.model;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Camera {
    private String brand;
    private String name;
    private String otherName;
    private String sensorSize;
    private double price;
    private String publishDate;
    private String material;
    private String pixel;
    private String processor;
    private String iso;
    private String imageStabilization;
    private String mount;
    private String focusPoints;
    private String screenType;
    private String screenSize;
    private String screenPixel;
    private String screenTouchable;
    private String viewfinder;
    private String magnificationRatio;
    private String viewfinderResolution;
    private String maximumShutterSpeed;
    private String electronicShutter;
    private String builtinFlash;
    private String externalFlash;
    private String flashSyncSpeed;
    private String continuousShooting;
    private String videoSpecs;
    private String microphone;
    private String usb;
    private String usbCharging;
    private String hdmi;
    private String microphonePort;
    private String headphonePort;
    private String battery;
    private String batteryLife;
    private String weightWithBattery;
    private String productId;
    private long updateTime;
    private List<String> imageList;

    public void generateId() {
        String temp = this.brand + this.name;
        this.productId = DigestUtils.md5DigestAsHex(temp.getBytes(StandardCharsets.UTF_8));
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getSensorSize() {
        return sensorSize;
    }

    public void setSensorSize(String sensorSize) {
        this.sensorSize = sensorSize;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPixel() {
        return pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getImageStabilization() {
        return imageStabilization;
    }

    public void setImageStabilization(String imageStabilization) {
        this.imageStabilization = imageStabilization;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }

    public String getFocusPoints() {
        return focusPoints;
    }

    public void setFocusPoints(String focusPoints) {
        this.focusPoints = focusPoints;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getScreenPixel() {
        return screenPixel;
    }

    public void setScreenPixel(String screenPixel) {
        this.screenPixel = screenPixel;
    }

    public String getScreenTouchable() {
        return screenTouchable;
    }

    public void setScreenTouchable(String screenTouchable) {
        this.screenTouchable = screenTouchable;
    }

    public String getViewfinder() {
        return viewfinder;
    }

    public void setViewfinder(String viewfinder) {
        this.viewfinder = viewfinder;
    }

    public String getMagnificationRatio() {
        return magnificationRatio;
    }

    public void setMagnificationRatio(String magnificationRatio) {
        this.magnificationRatio = magnificationRatio;
    }

    public String getViewfinderResolution() {
        return viewfinderResolution;
    }

    public void setViewfinderResolution(String viewfinderResolution) {
        this.viewfinderResolution = viewfinderResolution;
    }

    public String getMaximumShutterSpeed() {
        return maximumShutterSpeed;
    }

    public void setMaximumShutterSpeed(String maximumShutterSpeed) {
        this.maximumShutterSpeed = maximumShutterSpeed;
    }

    public String getElectronicShutter() {
        return electronicShutter;
    }

    public void setElectronicShutter(String electronicShutter) {
        this.electronicShutter = electronicShutter;
    }

    public String getBuiltinFlash() {
        return builtinFlash;
    }

    public void setBuiltinFlash(String builtinFlash) {
        this.builtinFlash = builtinFlash;
    }

    public String getExternalFlash() {
        return externalFlash;
    }

    public void setExternalFlash(String externalFlash) {
        this.externalFlash = externalFlash;
    }

    public String getFlashSyncSpeed() {
        return flashSyncSpeed;
    }

    public void setFlashSyncSpeed(String flashSyncSpeed) {
        this.flashSyncSpeed = flashSyncSpeed;
    }

    public String getContinuousShooting() {
        return continuousShooting;
    }

    public void setContinuousShooting(String continuousShooting) {
        this.continuousShooting = continuousShooting;
    }

    public String getVideoSpecs() {
        return videoSpecs;
    }

    public void setVideoSpecs(String videoSpecs) {
        this.videoSpecs = videoSpecs;
    }

    public String getMicrophone() {
        return microphone;
    }

    public void setMicrophone(String microphone) {
        this.microphone = microphone;
    }

    public String getUsb() {
        return usb;
    }

    public void setUsb(String usb) {
        this.usb = usb;
    }

    public String getUsbCharging() {
        return usbCharging;
    }

    public void setUsbCharging(String usbCharging) {
        this.usbCharging = usbCharging;
    }

    public String getHdmi() {
        return hdmi;
    }

    public void setHdmi(String hdmi) {
        this.hdmi = hdmi;
    }

    public String getMicrophonePort() {
        return microphonePort;
    }

    public void setMicrophonePort(String microphonePort) {
        this.microphonePort = microphonePort;
    }

    public String getHeadphonePort() {
        return headphonePort;
    }

    public void setHeadphonePort(String headphonePort) {
        this.headphonePort = headphonePort;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(String batteryLife) {
        this.batteryLife = batteryLife;
    }

    public String getWeightWithBattery() {
        return weightWithBattery;
    }

    public void setWeightWithBattery(String weightWithBattery) {
        this.weightWithBattery = weightWithBattery;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
