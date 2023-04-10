/**
 * @jest-environment jsdom
 */
import { mount, createLocalVue } from '@vue/test-utils'

import component from 'views/front/mobile/Test.vue'
const log = (...args) => console.log(`${component.name}] - `, ...args)


// log("데이터 값", component.data());
const localVue = createLocalVue();

describe(`${component.name} 파일 유닛테스트`, () => {
  log("파라메타", component.probs);

  let wrapper; // 화면 구성

  beforeEach(() => {
    wrapper = mount(component, {
      localVue,
    })
  })

  it("데이터 단위 테스트 init", ()=>{
    
  })
})
