package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.One;
import com.mycompany.myapp.repository.OneRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing One.
 */
@RestController
@RequestMapping("/api")
public class OneResource {

    private final Logger log = LoggerFactory.getLogger(OneResource.class);

    @Inject
    private OneRepository oneRepository;

    /**
     * POST  /ones -> Create a new one.
     */
    @RequestMapping(value = "/ones",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<One> create(@Valid @RequestBody One one) throws URISyntaxException {
        log.debug("REST request to save One : {}", one);
        if (one.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new one cannot already have an ID").body(null);
        }
        One result = oneRepository.save(one);
        return ResponseEntity.created(new URI("/api/ones/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("one", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /ones -> Updates an existing one.
     */
    @RequestMapping(value = "/ones",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<One> update(@Valid @RequestBody One one) throws URISyntaxException {
        log.debug("REST request to update One : {}", one);
        if (one.getId() == null) {
            return create(one);
        }
        One result = oneRepository.save(one);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("one", one.getId().toString()))
                .body(result);
    }

    /**
     * GET  /ones -> get all the ones.
     */
    @RequestMapping(value = "/ones",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<One> getAll() {
        log.debug("REST request to get all Ones");
        return oneRepository.findAll();
    }

    /**
     * GET  /ones/:id -> get the "id" one.
     */
    @RequestMapping(value = "/ones/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<One> get(@PathVariable Long id) {
        log.debug("REST request to get One : {}", id);
        return Optional.ofNullable(oneRepository.findOne(id))
            .map(one -> new ResponseEntity<>(
                one,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ones/:id -> delete the "id" one.
     */
    @RequestMapping(value = "/ones/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete One : {}", id);
        oneRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("one", id.toString())).build();
    }
}
