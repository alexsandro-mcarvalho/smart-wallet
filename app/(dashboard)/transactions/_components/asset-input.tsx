"use client";

import { useState, type ComponentProps } from "react";
import { Combobox } from "@base-ui/react/combobox";
import { CheckIcon, ChevronDownIcon } from "lucide-react";

import Button from "@/components/button";
import Input from "@/components/input";
import useAssets from "@/hooks/useAssets";
import type { AssetSummaryResponse } from "@/types/assets";

type AssetInputProps = Pick<
  ComponentProps<typeof Input>,
  "id" | "ref" | "onBlur" | "aria-invalid" | "aria-describedby"
> & {
  name: string;
  value: number;
  onChange: (value: number) => void;
};

function getAssetLabel(asset: AssetSummaryResponse) {
  return `${asset.name} (${asset.symbol})`;
}

export default function AssetInput({ name, value, onChange, onBlur, ...inputProps }: AssetInputProps) {
  const [open, setOpen] = useState(false);
  const [search, setSearch] = useState("");
  const [inputValue, setInputValue] = useState("");
  const [selectedAsset, setSelectedAsset] = useState<AssetSummaryResponse | null>(null);
  const selectedValue = selectedAsset?.id === value ? selectedAsset : null;
  const { assets, isLoading, isLoadingMore, error, loadMore, retry } = useAssets({
    page: 0,
    size: 10,
    name: search,
    enabled: open,
  });
  const items =
    selectedValue && !assets.some((asset) => asset.id === selectedValue.id) ? [...assets, selectedValue] : assets;

  return (
    <Combobox.Root
      items={items}
      filteredItems={assets}
      filter={null}
      value={selectedValue}
      inputValue={inputValue}
      itemToStringLabel={getAssetLabel}
      isItemEqualToValue={(asset, selected) => asset.id === selected.id}
      open={open}
      onOpenChange={setOpen}
      onValueChange={(asset) => {
        setSelectedAsset(asset);
        setInputValue(asset ? getAssetLabel(asset) : "");
        setSearch("");
        onChange(asset?.id ?? 0);
      }}
      onInputValueChange={(nextValue, { reason }) => {
        setInputValue(nextValue);
        if (reason === "input-change" || reason === "input-clear") {
          setSearch(nextValue);
          if (selectedValue && nextValue !== getAssetLabel(selectedValue)) {
            setSelectedAsset(null);
            onChange(0);
          }
        }
      }}
    >
      <div className="relative [&_input]:pr-12">
        <Combobox.Input
          {...inputProps}
          render={<Input />}
          placeholder="Pesquise pelo nome do ativo"
          autoComplete="off"
          onBlur={onBlur}
          onFocus={(event) => event.currentTarget.select()}
        />
        <Combobox.Trigger
          aria-label="Mostrar ativos"
          className="absolute inset-y-0 right-2 flex w-8 items-center justify-center text-muted-foreground"
        >
          <ChevronDownIcon className="size-4" aria-hidden="true" />
        </Combobox.Trigger>
      </div>
      <input type="hidden" name={name} value={value || ""} />
      <Combobox.Portal>
        <Combobox.Positioner
          side="bottom"
          align="start"
          sideOffset={4}
          collisionAvoidance={{ side: "none", align: "shift", fallbackAxisSide: "none" }}
          className="z-50"
        >
          <Combobox.Popup className="w-(--anchor-width) overflow-hidden rounded-lg bg-popover text-popover-foreground shadow-md ring-1 ring-foreground/10">
            <Combobox.List
              className="max-h-42 overflow-y-auto overscroll-contain"
              aria-busy={isLoading || isLoadingMore}
              onScroll={(event) => {
                const { scrollTop, scrollHeight, clientHeight } = event.currentTarget;
                if (scrollHeight - scrollTop - clientHeight <= 56) void loadMore();
              }}
            >
              {(asset: AssetSummaryResponse) => (
                <Combobox.Item
                  key={asset.id}
                  value={asset}
                  className="flex h-14 cursor-pointer items-center gap-3 px-4 text-sm outline-none select-none data-highlighted:bg-accent data-highlighted:text-accent-foreground"
                >
                  <div className="flex min-w-0 flex-1 flex-col">
                    <span className="truncate font-medium text-text-1">{asset.name}</span>
                    <span className="truncate text-xs text-muted-foreground">{asset.symbol}</span>
                  </div>
                  <Combobox.ItemIndicator>
                    <CheckIcon className="size-4" aria-hidden="true" />
                  </Combobox.ItemIndicator>
                </Combobox.Item>
              )}
            </Combobox.List>
            {(isLoading || isLoadingMore) && (
              <p role="status" className="px-4 py-3 text-sm text-muted-foreground">
                Carregando ativos...
              </p>
            )}
            {!isLoading && !isLoadingMore && error && (
              <div className="flex flex-col gap-2 px-4 py-3">
                <p role="alert" className="text-sm text-destructive">
                  Não foi possível carregar os ativos.
                </p>
                <Button type="button" size="sm" variant="secondary" onClick={() => void retry()}>
                  Tentar novamente
                </Button>
              </div>
            )}
            {!isLoading && !isLoadingMore && !error && assets.length === 0 && (
              <p role="status" className="px-4 py-3 text-sm text-muted-foreground">
                Nenhum ativo encontrado.
              </p>
            )}
          </Combobox.Popup>
        </Combobox.Positioner>
      </Combobox.Portal>
    </Combobox.Root>
  );
}
