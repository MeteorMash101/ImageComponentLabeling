public class Pixel {
    // data members
    private int label; // label 0's and 1's
    private int order; // order of findings by BFS or DFS

    // constructor
    public Pixel(int label, int order) {
        this.label = label;
        this.order = order;
    }

    // getters & setters
    public int getLabel() {
        return label;
    }

    public int getOrder() {
        return order;
    }

    public void setLabel(int label) {
        if (label >= 0) {
            this.label = label;
        }
    }

    public void setOrder(int order) {
        if (order >= 0) {
            this.order = order;
        }
    }

    // convert to string suitable for output
    @Override
    public String toString() {
        String description = "(" + label + "," + order + ")";
        return description;
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pixel) {
            Pixel otherPixel = (Pixel) obj;
            boolean sameLabel;
            boolean sameOrder;
            if (this.label == otherPixel.getLabel()) {
                sameLabel = true;
            } else {
                sameLabel = false;
            }
            if (this.order == otherPixel.getOrder()) {
                sameOrder = true;
            } else {
                sameOrder = false;
            }
            return sameLabel && sameOrder;
        } else {
            return false;
        }
    }
}