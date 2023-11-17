package br.com.fiap.leiapramim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.leiapramim.entity.Audio;
import br.com.fiap.leiapramim.entity.Text;

@Repository
public interface AudioRepository extends JpaRepository<Audio, Integer> {

  Audio findByText(Text text);

}
