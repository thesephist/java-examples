class Main {

    public static void main(String[] args) {
        ImageManager source = new ImageManager("./images/input.jpg");
        ImageManager target = source.clone("output.jpg");

        Filter.rotate(target, 25);
        Filter.scale(target, 1.3);
        Filter.saturate(target, 3);
    }

}
