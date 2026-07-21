import React from 'react'
import ButtonClose from './../button/ButtonClose';

const CardBottomRightBar = () => {
  return (
    <div className='flex items-center gap-2 relative p-2 rounded-2xl hover:bg-white/20 '>
      <img className='w-[40px] h-[40px] rounded-[50%] bg-red-500'></img>
      <div>Pham Tân</div>
      <ButtonClose />
    </div>
  )
}

export default CardBottomRightBar