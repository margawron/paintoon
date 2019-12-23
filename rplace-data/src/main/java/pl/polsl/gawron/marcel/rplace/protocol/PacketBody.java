package pl.polsl.gawron.marcel.rplace.protocol;

public interface PacketBody {

    String serialize();
    PacketBody deserialize(String json);
}
