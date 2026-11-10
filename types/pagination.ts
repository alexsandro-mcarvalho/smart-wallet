export type PaginationResponse<T> = {
  data: T[];
  currentPage: number;
  size: number;
  totalItems: number;
  totalPages: number;
  hasNext: boolean;
  hasPrevious: boolean;
};

export type PaginationRequest = {
  page: number;
  size: number;
  sort?: string;
};

export type PaginatedSWRKey = PaginationRequest & {
  key: string;
};
