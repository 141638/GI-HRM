import { computed, onMounted, onUnmounted, ref } from "vue";

// check if current view resolution is mobile device's or not
const screenWidth = ref(screen.width);

// init window listener
const init = async () => {
    window.addEventListener("resize", handleWindowResizeEvent);
}

// destroy window listener
const destroy = async () => {
    window.removeEventListener("resize", handleWindowResizeEvent);
}

// update screenWidth ref value
const handleWindowResizeEvent = async () => {
    screenWidth.value = screen.width;
}


onMounted(async () => {
    await init();
});

onUnmounted(async () => {
    await destroy();
})

export const isMobile = computed(() => ref(screenWidth.value <= 760))