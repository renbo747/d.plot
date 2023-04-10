<template>
  <div class="divided-layout">
    <div class="container">
      <div class="container-inner d-flex">
        <article class="left-layout">
          <slot name="left-contents"></slot>
        </article>
        <aside class="right-layout">
          <slot name="right-contents"></slot>
        </aside>
      </div>
    </div>
  </div>
</template>
