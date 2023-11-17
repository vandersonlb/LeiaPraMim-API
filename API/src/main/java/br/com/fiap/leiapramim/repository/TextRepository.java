package br.com.fiap.leiapramim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.leiapramim.entity.Device;
import br.com.fiap.leiapramim.entity.Text;

@Repository
public interface TextRepository extends JpaRepository<Text, Integer> {

  List<Text> findAllByDevice(Device device);

}
