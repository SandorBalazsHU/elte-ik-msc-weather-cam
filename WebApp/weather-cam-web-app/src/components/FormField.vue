<template>
  <input v-model="value" placeholder="Type something" />
  <span>{{ errorMessage }}</span>
</template>

<script setup lang="ts">
import { useField } from "vee-validate";

function validateField(value: string) {
  if (!value) {
    return "this field is required";
  }

  if (value.length < 8) {
    return "this field must contain at least 8 characters";
  }

  return true;
}

const { value, errorMessage } = useField("fullName", validateField);
</script>

<style>
input,
span {
  display: block;
  font-size: 16px;
}
</style>
