package co.edu.eci.arep.AREPT7.service;

import co.edu.eci.arep.AREPT7.model.Stream;
import co.edu.eci.arep.AREPT7.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StreamService {

    @Autowired
    private StreamRepository streamRepository;

    public void init() {
        // Crear el stream principal si no existe
        if (streamRepository.findByName("main") == null) {
            Stream mainStream = new Stream();
            mainStream.setName("main");
            streamRepository.save(mainStream);
        }
    }

    public Stream getMainStream() {
        return streamRepository.findByName("main");
    }

    public Stream createStream(String name) {
        Stream stream = new Stream();
        stream.setName(name);
        return streamRepository.save(stream);
    }

    public List<Stream> getAllStreams() {
        return streamRepository.findAll();
    }

    public Optional<Stream> getStreamById(Long id) {
        return streamRepository.findById(id);
    }
}