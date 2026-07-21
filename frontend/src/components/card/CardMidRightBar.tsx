import React from 'react'
import Logo from './../Icon/Logo';

const CardMidRightBar = () => {
  return (
      <div className="w-full flex gap-1 p-2 rounded-2xl hover:bg-white/20">
          <Logo url="https://img.icons8.com/?size=100&id=48280&format=png&color=000000" />
          <div> Hôm nay là sinh nhật của 1 người nào đó</div>
      </div>
  );
}

export default CardMidRightBar