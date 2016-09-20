package server;

class ImageWrapper {
    byte[] image;
    boolean motionDetected;
    long timeStamp;
    int size;

    public ImageWrapper(int size, long timeStamp, boolean motionDetected, byte[] image) {
        this.size = size;
        this.timeStamp = timeStamp;
        this.motionDetected = motionDetected;
        this.image = image;

    }

    public ImageWrapper makeClone() {
        byte[] copyOfImage = new byte[image.length];
        System.arraycopy(image, 0, copyOfImage, 0, image.length);
        return new ImageWrapper(size, timeStamp, motionDetected, copyOfImage);
    }


}
