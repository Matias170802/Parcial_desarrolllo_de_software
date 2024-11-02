package org.example.services;

import org.example.entities.Persona;
import org.example.repositories.BaseRepository;
import org.example.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaServiceImpl extends BaseServiceImpl<Persona,Long> implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ApiClient apiClient;

    public PersonaServiceImpl(BaseRepository<Persona,Long> baseRepository){//Constructor que no lo usamos nosotros sino que spring boot obtiene las dependencias necesarias
        super(baseRepository);
    }
    public boolean verificarMutante(Persona persona) {
        try {
            // Convierte la secuencia de ADN de la persona en un formato adecuado para la API y realiza la solicitud
            String[] secuenciaADN = persona.getSecuenciaADN(); // Asumiendo que tienes un método para obtener la secuencia de ADN como una matriz
            return apiClient.isMutant(secuenciaADN); // Llama al método isMutant de ApiClient
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}