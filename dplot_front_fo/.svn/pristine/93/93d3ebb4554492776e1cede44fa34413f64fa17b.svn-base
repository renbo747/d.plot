module.exports = {
  preset: '@vue/cli-plugin-unit-jest',
  "moduleFileExtensions": ["js", "jsx", "css"],
  "moduleDirectories": ["node_modules", "src"],
  setupFilesAfterEnv: [
    './jest.setup.js'
  ],
  testMatch: [
    '<rootDir>/tests/**/*.spec.(js|jsx|ts|tsx)|**/__tests__/*.(js|jsx|ts|tsx)',
  ],
  "moduleNameMapper": {
      // "\\.(css|less)$": "<rootDir>/__mocks__/styleMock.js",
      "\\.(css|less|scss|sass)$": "identity-obj-proxy",
      "\\.(jpg|jpeg|png|gif|eot|otf|webp|svg|ttf|woff|woff2|mp4|webm|wav|mp3|m4a|aac|oga)$": "<rootDir>/test/jest/__mocks__/fileMock.js",
      '@/(.*)$': '<rootDir>/src/$1',
      '@root/(.*)$': '<rootDir>/src/$1',
      '@router/(.*)$': '<rootDir>/router/$1',
      '@js/(.*)$': '<rootDir>/src/js/$1',

      '@views/(.*)$': '<rootDir>/src/views/$1',
      '@views.mobile/(.*)$': '<rootDir>/src/views/front/mobile/$1',
      '@views.pc/(.*)$': '<rootDir>/src/views/front/pc/$1',
      '@views.admin/(.*)$': '<rootDir>/src/views/front/admin/$1',

      '@router.mobile/(.*)$': '<rootDir>/src/router/front/mobile/$1',
      '@router.pc/(.*)$': '<rootDir>/src/router/front/pc/$1',
      '@router.admin/(.*)$': '<rootDir>/src/router/front/admin/$1',
      
      '@assets.mobile/(.*)$': '<rootDir>/src/asset/mobile/$1',
      '@assets.pc/(.*)$': '<rootDir>/src/asset/pc/$1',
      '@assets.admin/(.*)$': '<rootDir>/src/asset/$1',
  },
}