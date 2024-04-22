<template>
    <Toast></Toast>
</template>
<script setup lang="ts">
import type { ToastRefDto } from '@/dto/ToastRefDto';
import Toast from 'primevue/toast';
import { useToast } from "primevue/usetoast";
import { watch } from 'vue';

const toast = useToast();

const toastRefDto = defineModel<ToastRefDto | undefined>('toast');
const showToast = (toastRefDto: ToastRefDto | undefined) => {
    if (toastRefDto) {
        toast.add({ severity: toastRefDto.severity, summary: toastRefDto.header, detail: toastRefDto.message, life: toastRefDto.life });
    }
}

watch(toastRefDto, (newValue) => {
    showToast(newValue);
})
</script>