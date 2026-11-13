"use client";

import { PaginationResponse } from "@/types/pagination";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "./ui/pagination";

type PaginationControlsProps = {
  paginationResponse: Omit<PaginationResponse<unknown>, "data">;
  onPageChange: (page: number) => void;
};

export default function PaginationControls({ paginationResponse, onPageChange }: PaginationControlsProps) {
  const { currentPage, totalPages, hasNext, hasPrevious } = paginationResponse;
  const pagesPerGroup = 3;
  const firstPage = Math.floor(currentPage / pagesPerGroup) * pagesPerGroup;
  const endPage = Math.min(firstPage + pagesPerGroup, totalPages);
  const pages = Array.from({ length: endPage - firstPage }, (_, index) => firstPage + index);

  if (totalPages === 0) return null;

  return (
    <div>
      <Pagination>
        <PaginationContent>
          <PaginationItem>
            <PaginationPrevious
              href="#"
              text="Anterior"
              aria-label="Ir para a página anterior"
              aria-disabled={!hasPrevious}
              tabIndex={hasPrevious ? 0 : -1}
              className={!hasPrevious ? "pointer-events-none opacity-50" : ""}
              onClick={(event) => {
                event.preventDefault();
                if (hasPrevious) onPageChange(currentPage - 1);
              }}
            />
          </PaginationItem>
          {firstPage > 0 && (
            <PaginationItem>
              <PaginationEllipsis />
            </PaginationItem>
          )}
          {pages.map((page) => (
            <PaginationItem key={page}>
              <PaginationLink
                href="#"
                isActive={page === currentPage}
                aria-label={`Ir para a página ${page + 1}`}
                onClick={(event) => {
                  event.preventDefault();
                  if (page !== currentPage) onPageChange(page);
                }}
              >
                {page + 1}
              </PaginationLink>
            </PaginationItem>
          ))}
          {endPage < totalPages && (
            <PaginationItem>
              <PaginationEllipsis />
            </PaginationItem>
          )}
          <PaginationItem>
            <PaginationNext
              href="#"
              text="Próxima"
              aria-label="Ir para a próxima página"
              aria-disabled={!hasNext}
              tabIndex={hasNext ? 0 : -1}
              className={!hasNext ? "pointer-events-none opacity-50" : ""}
              onClick={(event) => {
                event.preventDefault();
                if (hasNext) onPageChange(currentPage + 1);
              }}
            />
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    </div>
  );
}
