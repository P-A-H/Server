package fr.tcd.server.document;

import fr.tcd.server.document.exception.DocumentNotCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<String> createDocument(@Valid @RequestBody DocumentDTO documentDTO,
                                         @RequestParam(required = false, name = "file") MultipartFile file,
                                                 Principal principal, UriComponentsBuilder uriBuilder) {

        //TODO: Do a Switch on Type of data between file or link and process
        String newDocumentId = documentService.createDocument(documentDTO, principal.getName()).getId();
        URI location = uriBuilder.path("/document/{docId}").build(newDocumentId);

        return ResponseEntity.created(location).body(newDocumentId);
    }

    // ============== NON-API ==============

}
