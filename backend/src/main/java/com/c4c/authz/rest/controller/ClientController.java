package com.c4c.authz.rest.controller;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.CLIENT_URL;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.rest.resource.ClientResource;
import com.c4c.authz.rest.resource.PagedModelResponse;
import jakarta.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Client controller.
 */
@Slf4j
@RestController
@RequestMapping(ClientController.BASE_URL)
public class ClientController extends BaseController {
  /**
   * The constant BASE_URL.
   */
  static final String BASE_URL = API_V1 + CLIENT_URL;

  /**
   * Instantiates a new Client controller.
   *
   * @param restAdapterV1 the rest adapter v 1
   */
  @Autowired
  protected ClientController(final RestAdapterV1 restAdapterV1) {
    super(restAdapterV1);
  }

  /**
   * Find by id response entity.
   *
   * @param clientId the client id
   * @return the response entity
   */
  @GetMapping("/{clientId}")
  public ResponseEntity<ClientResource> findById(@PathVariable("clientId") final UUID clientId) {
    ClientResource resource = this.getRestAdapterV1().findByIdClient(clientId);
    if (!Objects.isNull(resource)) {
      return ResponseEntity.ok().body(resource);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Find by pagination response entity.
   *
   * @param pageIndex     the page index
   * @param pageSize      the page size
   * @param sortDirection the sort direction
   * @param sortField     the sort field
   * @return the response entity
   */
  @GetMapping
  public ResponseEntity<PagedModelResponse<ClientResource>> findByPagination(
          @RequestParam(value = "pageIndex", required = false, defaultValue = "-1") final int pageIndex,
          @RequestParam(value = "pageSize", required = false, defaultValue = "-1") final int pageSize,
          @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") final String sortDirection,
          @RequestParam(value = "sortField", required = false, defaultValue = "name") final String sortField) {
    Pageable pageRequest = getPageableRequest(pageIndex, pageSize, sortDirection, sortField);
    if (pageSize > 0) {
      Page<ClientResource> resources = this.getRestAdapterV1().findByPaginationClient(pageRequest);
      return ResponseEntity.ok().body(new PagedModelResponse<>(resources));
    } else {
      List<ClientResource> resources = this.getRestAdapterV1().findAllClient();
      return ResponseEntity.ok().body(new PagedModelResponse<>(new PageImpl<>(resources)));
    }
  }

  /**
   * Create response entity.
   *
   * @param name the name
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<ClientResource> create(final @RequestParam @NotEmpty String name) {
    ClientResource client = new ClientResource();
    client.setName(name);
    client.setTenantId(CurrentUserContext.getCurrentTenantId());
    ClientResource resource = this.getRestAdapterV1().createClient(client);
    return ResponseEntity.created(URI.create(BASE_URL + "/" + resource.getId())).body(resource);
  }

  /**
   * Update response entity.
   *
   * @param client the client
   * @param id     the id
   * @return the response entity
   */
  @PutMapping("/{id}")
  public ResponseEntity<ClientResource> update(final @RequestBody @Validated ClientResource client,
                                               @PathVariable("id") final UUID id) {
    client.setId(id);
    client.setTenantId(CurrentUserContext.getCurrentTenantId());
    ClientResource resource = this.getRestAdapterV1().updateClient(client);
    return ResponseEntity.ok().body(resource);
  }

  /**
   * Delete by id response entity.
   *
   * @param clientId the client id
   * @return the response entity
   */
  @DeleteMapping("/{clientId}")
  public ResponseEntity<Void> deleteById(@PathVariable("clientId") final UUID clientId) {
    this.getRestAdapterV1().deleteByIdClient(clientId);
    return ResponseEntity.noContent().build();
  }
}
