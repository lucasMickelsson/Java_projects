package ax.ha.scp.rest.Controller;
import ax.ha.scp.rest.Models.Anomaly;
import ax.ha.scp.rest.Models.AnomalyRepository;
import ax.ha.scp.rest.Models.Observation;
import ax.ha.scp.rest.Models.ObservationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class ApiControllers {
    @Autowired
    private AnomalyRepository anomalyRepo;

    @Autowired
    private ObservationRepository observationRepo;

    @GetMapping(value = "/")
    public String getPage() {
        return "Welcome to the SCP Rest API";
    }

    @Operation(summary = "Get anomalies", description = "Get a list of anomalies", tags = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found anomalies",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AnomalyRepository.class))}),
            @ApiResponse(responseCode = "404", description = "Anomalies not found",
            content = @Content),
    })
    @GetMapping(value = "/anomalies")
    public ResponseEntity<List<Anomaly>> getAnomalies() {
        List<Anomaly> anomalies = (List<Anomaly>) anomalyRepo.findAll();
        if(anomalies.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(anomalies);
        }
    }

    @Operation(summary = "Get anomaly", description = "Get an anomaly by id", tags = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found anomaly",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AnomalyRepository.class))}),
            @ApiResponse(responseCode = "404", description = "Anomaly not found",
                    content = @Content),
    })
    @GetMapping(value = "/anomalies/{id}")
    public ResponseEntity<Anomaly> getAnomalyById(@PathVariable String id) {
        Optional<Anomaly> anomaly = anomalyRepo.findById(id);
        if(anomaly.isPresent()) {
            return ResponseEntity.ok(anomaly.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Get anomaly and observations", description = "Get an anomaly by id and its observations", tags = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found anomaly and observations",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AnomalyRepository.class))}),
            @ApiResponse(responseCode = "404", description = "Anomaly and observation not found",
                    content = @Content),
    })
    @GetMapping(value = "/anomalyobservations/{anomalyId}")
    public ResponseEntity<List<Observation>> getObservationsByAnomaly(@PathVariable String anomalyId) {
        Anomaly anomalyToFind = anomalyRepo.findById(anomalyId)
                .orElseThrow(() -> new NoSuchElementException("Anomaly not found"));
        List<Observation> observations = observationRepo.findByAnomaly(anomalyToFind);
        return ResponseEntity.ok(observations);
    }

    @Operation(summary = "Create anomaly", description = "Add an anomaly", tags = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anomaly added",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AnomalyRepository.class))}),
            @ApiResponse(responseCode = "500", description = "The anomaly already exists",
                    content = @Content),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value= "/anomalies")
    public ResponseEntity<String> saveAnomaly(@RequestBody Anomaly anomaly) {
       String id = anomaly.getId();
       if(anomalyRepo.findById(id).isPresent()) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The anomaly already exists");
       }
       else{
           anomalyRepo.save(anomaly);
           return ResponseEntity.ok(anomaly.getId());
       }

    }
    @Operation(summary = "Update anomaly", description = "Update existing anomaly", tags = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anomaly updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AnomalyRepository.class))}),
            @ApiResponse(responseCode = "404", description = "Anomaly not found",
                    content = @Content),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/anomalies/{id}")
    public ResponseEntity<Anomaly> updateAnomaly(@PathVariable String id, @RequestBody Anomaly anomaly) {
        if(anomalyRepo.findById(id).isPresent()){
            Anomaly updatedAnomaly = anomalyRepo.findById(id).get();
            updatedAnomaly.setCategory(anomaly.getCategory());
            updatedAnomaly.setDescription(anomaly.getDescription());
            anomalyRepo.save(updatedAnomaly);
            return ResponseEntity.ok(updatedAnomaly);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete anomaly", description = "Delete an anomaly", tags = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anomaly deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AnomalyRepository.class))}),
            @ApiResponse(responseCode = "404", description = "Anomaly not found",
                    content = @Content),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/anomalies/{id}")
    public ResponseEntity<Anomaly> deleteAnomaly(@PathVariable String id) {
        if(anomalyRepo.findById(id).isPresent()){
            Anomaly deleteAnomaly = anomalyRepo.findById(id).get();
            anomalyRepo.delete(deleteAnomaly);
            return ResponseEntity.ok(deleteAnomaly);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(summary = "Get observation", description = "Get a list of all observations", tags = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found observations",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ObservationRepository.class))}),
            @ApiResponse(responseCode = "404", description = "Observations not found",
                    content = @Content),
    })
    @GetMapping(value = "/observations")
    public ResponseEntity<List<Observation>> getObservations() {
        List<Observation> observations = (List<Observation>) observationRepo.findAll();
        if (observations.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(observations);
        }
    }
    @Operation(summary = "Get observation by id", description = "Get observation by an id", tags = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got observation by this id",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ObservationRepository.class))}),
            @ApiResponse(responseCode = "404", description = "No observations was found for the given id",
                    content = @Content),
    })
    @GetMapping(value = "/observations/{id}")
    public ResponseEntity<Observation> getObservationById(@PathVariable Long id) {
        Optional<Observation> observation = observationRepo.findById(id);
        if(observation.isPresent()) {
            return ResponseEntity.ok(observation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Create observation", description = "Add a new observation", tags = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Observation added",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ObservationRepository.class))}),
            @ApiResponse(responseCode = "500", description = "The observation already exists",
                    content = @Content),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value= "/observations")
    public ResponseEntity<String> saveObservation(@RequestBody Observation observation) {
        try {
            if(anomalyRepo.findById(observation.getAnomaly().getId()).isPresent()) {
                // create observation
                Observation createdObservation = new Observation();
                createdObservation.setDate(observation.getDate());
                createdObservation.setLocation(observation.getLocation());
                createdObservation.setDescription(observation.getDescription());
                observationRepo.save(createdObservation);

                // add the anomaly to the newly created observation
                String anomalyId = observation.getAnomaly().getId();
                Anomaly anomaly = anomalyRepo.findById(anomalyId)
                        .orElseThrow(() -> new NoSuchElementException("Anomaly not found"));
                createdObservation.setAnomaly(anomaly);
                observationRepo.save(createdObservation);

                return ResponseEntity.ok("Observation saved...");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save observation!");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save observation: " + e.getMessage());
        }
    }

    @Operation(summary = "Update observation", description = "Update existing observation", tags = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Observation updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ObservationRepository.class))}),
            @ApiResponse(responseCode = "404", description = "observation not found",
                    content = @Content),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/observations/{id}")
    public ResponseEntity<String> updateObservation(@PathVariable Long id, @RequestBody Observation observation) {
        try {
            Observation updatedObservation = observationRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Observation not found"));
            updatedObservation.setAnomaly(observation.getAnomaly());
            updatedObservation.setDate(observation.getDate());
            updatedObservation.setLocation(observation.getLocation());
            updatedObservation.setDescription(observation.getDescription());
            observationRepo.save(updatedObservation);
            return ResponseEntity.ok("Observation " + id + " updated...");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Observation not found");
        }
    }

    @Operation(summary = "Delete observation", description = "Delete a observation", tags = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted observation successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ObservationRepository.class))}),
            @ApiResponse(responseCode = "404", description = "Could not delete observation, not founded",
                    content = @Content),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "observations/{id}")
    public ResponseEntity<String> deleteObservation(@PathVariable Long id) {
        if (observationRepo.findById(id).isPresent()){
            Observation deleteObservation = observationRepo.findById(id).get();
            deleteObservation.setAnomaly(null);
            observationRepo.delete(deleteObservation);
            return ResponseEntity.ok("Observation " + id + " deleted...");
        }
        else{
            return ResponseEntity.badRequest().body("Observation not found");
        }
    }
}