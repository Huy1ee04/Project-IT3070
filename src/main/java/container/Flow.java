package container;

import java.util.LinkedList;
import item.Packet;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Flow implements IContainer {
    private String nameFlow;
    private int weight;
    private  LinkedList<Packet> queue;
    private HBox hbox;
    private Text resourceAlloc = new Text("Allocation: 0");
    private Text resourceReturn = new Text("Return: 0");
    private int resorceAllocation;
    private int resourceRecently;
    private LinkedList<Packet> packetOutList = new LinkedList<>();

    public Flow(int weight) {
        this.weight = weight;
        this.queue = new LinkedList<>();
    }
    public Flow(){

    }
    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public void addPacket(Packet packet) {
        queue.add(packet);
    }

    // Getter và Setter cho weight
    public int getWeight() {
        return weight;
    }

    public void setHbox(HBox hbox) {
        this.hbox = hbox;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public HBox getHbox() {
        return hbox;
    }

    public void setNameFlow(String nameFlow) {
        this.nameFlow = nameFlow;
    }

    public String getNameFlow() {
        return this.nameFlow;
    }

    public void setResorceAlled(int resourceAllocation) {
        this.resorceAllocation += resourceAllocation;
        resourceAlloc.setText("Allocation: " + this.resorceAllocation);
        resourceRecently = resourceAllocation;
    }

    public int getResourceRecently() {
        return resourceRecently;
    }

    public void setPackageOut() {
        while (!queue.isEmpty() && resorceAllocation >= queue.getFirst().getSizePackage()) {
            Packet pkg = queue.removeFirst();
            resorceAllocation -= pkg.getSizePackage();
            packetOutList.add(pkg);
        }
    }

    public void updateHbox() {
        Platform.runLater(() -> {
            while (!packetOutList.isEmpty()) {
                Packet pkg = packetOutList.removeFirst();
                if (!hbox.getChildren().isEmpty()) {
                    hbox.getChildren().removeFirst();
                }
                pkg.setSrcFlow(this);
            }
        });
    }

    public void clearPackageOut(){
        packetOutList.clear();
    }

    public LinkedList<Packet> getPackageOut() {
        return this.packetOutList;
    }

    public int returnResource() {
        if (!isEmpty()) return 0;
        else {
            int resource = this.resorceAllocation;
            resourceReturn.setText("Return: " + resource);
            this.resorceAllocation = 0;
            return resource;
        }
    }

    public int getResorceAllocation() {
        return resorceAllocation;
    }

    public Text getResourceAlloc() {
        return resourceAlloc;
    }

    public void setResourceAlloc(Text resourceAlloc) {
        this.resourceAlloc = resourceAlloc;
    }

    public Text getResourceReturn() {
        return resourceReturn;
    }

    public void setResourceReturn(Text resourceReturn) {
        this.resourceReturn = resourceReturn;
    }
}
