package transmisstion;

import java.util.ArrayList;
import java.util.List;
import Item.PackageItem; // Import lớp Package từ gói Item
import javafx.scene.layout.HBox;

public class QueueOut {
    private final List<PackageItem> outgoingQueue;
    private int throughput; // Thuộc tính đại diện cho thông lượng của hàng đợi
    private HBox hBox;

    public QueueOut() {
        this.outgoingQueue = new ArrayList<>();
        this.throughput = 0; // Khởi tạo thông lượng ban đầu là 0
    }

    // Phương thức để thêm gói tin vào hàng đợi
    public void addPackage(PackageItem packet) {
        outgoingQueue.add(packet);
    }

    // Phương thức để lấy gói tin từ hàng đợi
    public PackageItem retrievePackage() {
        if (!outgoingQueue.isEmpty()) {
            return outgoingQueue.remove(0); // Lấy và loại bỏ gói tin đầu tiên
        }
        return null; // Trả về null nếu hàng đợi rỗng
    }

    // Phương thức để kiểm tra xem hàng đợi có rỗng không
    public boolean isEmpty() {
        return outgoingQueue.isEmpty();
    }

    // Phương thức in ra thông tin của các gói tin trong hàng đợi
    public void printQueueInfo() {
        System.out.println("Outgoing Queue Information:");
        for (PackageItem packet : outgoingQueue) {
            System.out.println("Packet Size: " + packet.getSizePackage());
            // In thêm các thông tin khác của gói tin nếu cần
        }
    }

    // Getter và Setter cho thông lượng
    public int getThroughput() {
        return throughput;
    }

    public void setThroughput(int throughput) {
        this.throughput = throughput;
    }
    public void sethBox(HBox hBox){
        this.hBox = hBox;
    }
    public HBox gethBox(){
        return this.hBox;
    }


}
