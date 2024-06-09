package transmisstion;

import java.util.ArrayList;
import java.util.List;
import item.Packet; // Import lớp Package từ gói Item
import javafx.scene.layout.HBox;

public class QueueOut implements ITransLine{
    private final List<Packet> outgoingQueue;
    private int throughput; // Thuộc tính đại diện cho thông lượng của hàng đợi
    private HBox hBox;

    public QueueOut() {
        this.outgoingQueue = new ArrayList<>();
        this.throughput = 0; // Khởi tạo thông lượng ban đầu là 0
    }

    @Override
    public boolean isEmpty() {
        return outgoingQueue.isEmpty();
    }

    @Override
    public <Packet> void add(Packet packet) {
        this.outgoingQueue.add((item.Packet) packet);
    }

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
