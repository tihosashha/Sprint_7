package courier;

public class CourierDeleteJson {
    public long id;

    public CourierDeleteJson() {
    }

    public CourierDeleteJson(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CourierDeleteJson{" +
                "id=" + id +
                '}';
    }
}
