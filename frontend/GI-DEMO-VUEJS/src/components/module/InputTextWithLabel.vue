<template>
    <div>
        <div class="flex flex-column gap-2">
            <label :for="inputId">{{ label }}</label>
            <InputText :disabled="disabled" :id="inputId" v-model="value" :aria-describedby="inputId + '-help'">
            </InputText>
        </div>
    </div>
</template>
<script setup lang="ts">
import InputText from 'primevue/inputtext';

const disabled = defineModel<boolean>('disabled', { default: false });
const label = defineModel<string>('label', { required: true });
const inputId = label.value.trim().toLowerCase().replace(/\s/g, '-') + '-id';
const value = defineModel<string>('value');
</script>
<style lang="scss"></style>