package item;

import container.Flow;
import javafx.scene.Node;

public class Packet implements ITransUnit {
    private final int sizePackage; // Kích thước của package
    private int resourceAlloc; // Tài nguyên cấp phát cho package
    private int orderTrans; // Thứ tự truyền tải
    private Flow srcFlow; // Luồng nguồn
    private Node node;

    // Constructor
    public Packet(int sizePackage) {
        this.sizePackage = sizePackage;
    }

    // Getter cho kích thước package
    public int getSizePackage() {
        return sizePackage;
    }



    // Getter và Setter cho tài nguyên cấp phát
    public int getResourceAlloc() {
        return resourceAlloc;
    }

    public void setResourceAlloc(int resourceAlloc) {
        this.resourceAlloc = resourceAlloc;
    }

    // Getter và Setter cho thứ tự truyền tải
    public int getOrderTrans() {
        return orderTrans;
    }

    public void setOrderTrans(int orderTrans) {
        this.orderTrans = orderTrans;
    }

    // Getter và Setter cho luồng nguồn

    public void setSrcFlow(Flow srcFlow) {
        this.srcFlow = srcFlow;
    }

    public Node getNode() {
        return this.node;
    }
    public void setNode(Node node){
        this.node = node;
    }

    @Override
    public void showDetail() {
        System.out.println(this.sizePackage);
        System.out.println(this.orderTrans);
    }
}
