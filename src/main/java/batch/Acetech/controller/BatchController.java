package batch.Acetech.controller;

import batch.Acetech.model.Batch;
import batch.Acetech.model.BatchResponse;
import batch.Acetech.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/batches")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @PostMapping(path="/createBatch" , consumes = "application/json")
    public ResponseEntity<BatchResponse> receiveBatches(@RequestBody List<Batch> batches) {
        BatchResponse batchResponse = batchService.createBatch(batches);
        return ResponseEntity.status(HttpStatus.OK).body(batchResponse);
    }
}