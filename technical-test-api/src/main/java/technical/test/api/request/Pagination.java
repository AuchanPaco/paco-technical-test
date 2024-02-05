package technical.test.api.request;

public record Pagination(int page, int size) {

  public long getSkippedDocuments() {
    return (long) (page - 1) * size;
  }
}
