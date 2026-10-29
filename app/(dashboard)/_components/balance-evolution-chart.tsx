"use client";

import { Area, AreaChart, XAxis, YAxis } from "recharts";

import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { ChartContainer, ChartTooltip, ChartTooltipContent, type ChartConfig } from "@/components/ui/chart";
import { formatCurrency } from "@/lib/utils/formatter";
import { TrendingUp } from "lucide-react";

const chartData = [
  { period: "17 jan", saldo: 1200 },
  { period: "18 jan", saldo: 1550 },
  { period: "19 jan", saldo: 1380 },
  { period: "20 jan", saldo: 1800 },
  { period: "21 jan", saldo: 2100 },
  { period: "22 jan", saldo: 2450 },
];

// Five equal intervals, rounded up to a step of 1, 2 or 5 times a power of ten.
const maxBalance = Math.max(1, ...chartData.map(({ saldo }) => saldo));
const rawStep = maxBalance / 5;
const magnitude = 10 ** Math.floor(Math.log10(rawStep));
const step = ([1, 2, 5, 10].find((value) => value * magnitude >= rawStep) ?? 10) * magnitude;
const balanceTicks = Array.from({ length: 6 }, (_, index) => Number((index * step).toPrecision(12)));
const axisFormatter = new Intl.NumberFormat("pt-BR", { maximumFractionDigits: 2 });

function formatAxisValue(value: number): string {
  if (Math.abs(value) >= 1_000_000) {
    return `R$ ${axisFormatter.format(value / 1_000_000)}M`;
  }

  if (Math.abs(value) >= 1_000) {
    return `R$ ${axisFormatter.format(value / 1_000)}K`;
  }

  return `R$ ${axisFormatter.format(value)}`;
}

const chartConfig = {
  saldo: {
    label: "Saldo",
    color: "var(--color-lime-300)",
  },
} satisfies ChartConfig;

export function BalanceEvolutionChart() {
  return (
    <Card className="w-3xl w-full min-w-0 rounded-lg border border-gray-500 font-kumbh ring-0">
      <CardHeader>
        <CardTitle className="font-kumbh text-lg font-semibold text-text-1">Histórico do Saldo</CardTitle>
      </CardHeader>
      <CardContent className="min-w-0">
        <ChartContainer config={chartConfig} className="aspect-auto h-[200px] max-h-[200px] w-full min-w-0">
          <AreaChart accessibilityLayer data={chartData} margin={{ left: 12, right: 12, top: 12 }}>
            <YAxis
              tick={{ fontFamily: "var(--font-kumbh-sans)" }}
              domain={[0, balanceTicks[5]]}
              ticks={balanceTicks}
              interval={0}
              tickLine={false}
              axisLine={false}
              tickMargin={8}
              width="auto"
              tickFormatter={formatAxisValue}
            />
            <XAxis
              tick={{ fontFamily: "var(--font-kumbh-sans)" }}
              dataKey="period"
              tickLine={false}
              axisLine={false}
              tickMargin={8}
              minTickGap={24}
              interval="preserveStartEnd"
              padding={{ left: 16, right: 16 }}
            />
            <ChartTooltip
              cursor={false}
              content={
                <ChartTooltipContent
                  className="font-kumbh"
                  formatter={(value) => (
                    <div className="flex w-full items-center justify-between gap-4">
                      <span className="text-muted-foreground">Saldo</span>
                      <span className="font-medium tabular-nums">{formatCurrency(Number(value))}</span>
                    </div>
                  )}
                />
              }
            />
            <Area
              dataKey="saldo"
              type="monotone"
              stroke="var(--color-saldo)"
              strokeWidth={2}
              fill="var(--color-saldo)"
              fillOpacity={0.25}
              dot={false}
            />
          </AreaChart>
        </ChartContainer>
      </CardContent>
    </Card>
  );
}
