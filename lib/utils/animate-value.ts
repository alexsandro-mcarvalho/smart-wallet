/** Animates from zero to the target and returns a function to cancel the animation. */
export function animateValue(target: number, onUpdate: (value: number) => void, duration = 1000): () => void {
  let frameId: number;
  let startTime: number | undefined;

  function update(timestamp: number) {
    startTime ??= timestamp;

    const progress = duration <= 0 ? 1 : Math.min((timestamp - startTime) / duration, 1);
    const easedProgress = 1 - Math.pow(1 - progress, 3);

    onUpdate(progress === 0 ? 0 : progress === 1 ? target : target * easedProgress);

    if (progress < 1) {
      frameId = requestAnimationFrame(update);
    }
  }

  frameId = requestAnimationFrame(update);

  return () => cancelAnimationFrame(frameId);
}
