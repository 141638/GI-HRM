import { isRef, onBeforeUnmount, onMounted, toValue, watch, type Ref } from "vue";

export function useEventListener(
    target: Ref<EventTarget | null> | EventTarget,
    event: string,
    handler: () => any
) {
    const addEvent = (target: EventTarget | null) => {
        target?.addEventListener(event, handler);
    }
    const removeEvent = (target: EventTarget | null) => {
        target?.removeEventListener(event, handler);
    }

    if (isRef(target)) {
        watch(target, (old, current) => {
            removeEvent(old);
            addEvent(current);
        });
    } else {
        onMounted(() => {
            addEvent(target);
        });
    }

    onBeforeUnmount(() => {
        removeEvent(toValue(target));
    })
}