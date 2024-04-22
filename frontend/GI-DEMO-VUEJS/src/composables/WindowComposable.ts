import { computed, ref } from "vue";
import { useEventListener } from "./EventComposable";

export function useIsMobile() {
    const mobileWidthLimitPx = 760;
    const currentScreenWidth = ref(window.screen.width);
    const isMobile = ref(computed(() => currentScreenWidth.value <= mobileWidthLimitPx));

    useEventListener(window, 'resize', () => {
        currentScreenWidth.value = window.screen.width;
    });

    return isMobile;
}