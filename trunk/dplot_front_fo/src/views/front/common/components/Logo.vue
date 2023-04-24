<template>
  <div class="d-flex align-items-center">
    <template v-if="isPageLogo">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="139"
        height="27"
        viewBox="0 0 139 27"
      >
        <defs>
          <clipPath id="ddx70h09ha">
            <path
              data-name="사각형 7522"
              style="fill: none"
              d="M0 0h109.033v20.524H0z"
            />
          </clipPath>
        </defs>
        <path data-name="사각형 7462" style="fill: none" d="M0 0h116v26H0z" />
        <g data-name="그룹 23694">
          <path
            data-name="패스 17940"
            d="M56.092 2.999v20.524H74.05v-3.848H59.94V2.999z"
          />
          <path
            data-name="패스 17941"
            d="M94.575 2.999v3.849h7.055v16.675h3.845V6.848h7.055V2.999z"
          />
          <g data-name="그룹 23693">
            <g
              data-name="그룹 23692"
              style="clip-path: url(#ddx70h09ha)"
              transform="translate(3.5 2.999)"
            >
              <path
                data-name="패스 17942"
                d="M10.262 0H0v20.524h10.262a10.262 10.262 0 1 0 0-20.524m0 16.676H3.848V3.848h6.414a6.414 6.414 0 0 1 0 12.827"
              />
              <path
                data-name="패스 17943"
                d="M80.813 0a10.262 10.262 0 1 0 10.262 10.262A10.262 10.262 0 0 0 80.813 0m0 16.676a6.414 6.414 0 1 1 6.414-6.414 6.413 6.413 0 0 1-6.414 6.414"
              />
              <path
                data-name="패스 17944"
                d="M42.969 0h-10.9v20.524h3.848V14.11h7.052a7.055 7.055 0 1 0 0-14.11m0 10.262h-7.052V3.848h7.052a3.207 3.207 0 1 1 0 6.414"
              />
              <path
                data-name="패스 17945"
                d="M23.089 15.393a2.565 2.565 0 1 0 2.565 2.565 2.565 2.565 0 0 0-2.565-2.565"
              />
            </g>
          </g>
        </g>
      </svg>
    </template>
    <template v-else>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="80"
        height="18"
        viewBox="0 0 80 18"
      >
        <defs>
          <clipPath id="2e31qfq28a">
            <path
              data-name="사각형 7461"
              :style="{ fill: `${logoColor}` }"
              d="M0 0h71.695v13.496H0z"
            />
          </clipPath>
        </defs>
        <g data-name="그룹 22823">
          <path
            data-name="패스 17912"
            d="M102.5 0v13.5h11.809v-2.53h-9.278V0z"
            transform="translate(-64.613 2.469)"
            class="logo-line"
            :style="{ fill: `${logoColor}` }"
          />
          <g data-name="그룹 22822">
            <g data-name="그룹 22821">
              <path
                data-name="패스 17913"
                d="M177.5 0v2.53h4.639V13.5h2.53V2.531h4.639V0z"
                transform="translate(-111.309 2.469)"
                class="logo-line"
                :style="{ fill: `${logoColor}` }"
              />
              <g data-name="그룹 22817">
                <g
                  data-name="그룹 22816"
                  style="clip-path: url(#2e31qfq28a)"
                  transform="translate(1.305 2.469)"
                >
                  <path
                    data-name="패스 17914"
                    d="M6.748 0H0v13.5h6.748a6.748 6.748 0 0 0 0-13.5m0 10.965H2.53V2.53h4.218a4.217 4.217 0 1 1 0 8.435"
                    class="logo-line"
                    :style="{ fill: `${logoColor}` }"
                  />
                  <path
                    data-name="패스 17915"
                    d="M144.248 0A6.748 6.748 0 1 0 151 6.748 6.748 6.748 0 0 0 144.248 0m0 10.965a4.217 4.217 0 1 1 4.217-4.217 4.217 4.217 0 0 1-4.217 4.217"
                    transform="translate(-88.109)"
                    class="logo-line"
                    :style="{ fill: `${logoColor}` }"
                  />
                  <path
                    data-name="패스 17916"
                    d="M69.67 0H62.5v13.5h2.53V9.278h4.64a4.639 4.639 0 1 0 0-9.278m0 6.748h-4.64V2.53h4.64a2.109 2.109 0 1 1 0 4.217"
                    transform="translate(-40.413)"
                    class="logo-line"
                    :style="{ fill: `${logoColor}` }"
                  />
                  <path
                    data-name="패스 17917"
                    d="M41.687 30a1.687 1.687 0 1 0 1.687 1.687A1.687 1.687 0 0 0 41.687 30"
                    transform="translate(-25.504 -19.878)"
                    class="logo-line"
                    :style="{ fill: `${logoColor}` }"
                  />
                </g>
              </g>
            </g>
          </g>
        </g>
      </svg>
    </template>
  </div>
</template>

<script>
export default {
  props: {
    isPageLogo: {
      type: Boolean,
      default: true,
    },
    logoColor: {
      default: "#000000",
    },
  },
};
</script>

<style scoped>
svg {
  width: 100%;
  height: 100%;
}
</style>
