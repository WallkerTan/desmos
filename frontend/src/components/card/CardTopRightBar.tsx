import React from 'react'

const CardTopRightBar = () => {
  return (
      <div className="w-full h-[45%] text-white flex justify-around gap-1  my-2 rounded-2xl p-2 hover:bg-white/20">
          <img
              src="https://scontent.fhan14-2.fna.fbcdn.net/v/t39.30808-6/690499521_968595212597626_5316291266711040752_n.jpg?stp=dst-jpg_tt6&cstp=mx960x958&ctp=s960x958&_nc_cat=108&ccb=1-7&_nc_sid=6ee11a&_nc_eui2=AeFmpuUl_Tx78R6GdH48MsQU6tIa_x6kCuDq0hr_HqQK4BiutygIMG0g3hcarZPg7hffsRQ2vsbHhQWgi4anCGZr&_nc_ohc=rB3ZBxZMKakQ7kNvwGS5EI6&_nc_oc=Adpfy7vBDsVpjn_SscZ3ywgFV8uUq03gYy121waDdyattL96BVcCf40ZCcPLtWLeuPA&_nc_zt=23&_nc_ht=scontent.fhan14-2.fna&_nc_gid=1YJb1cbah2hNFp5Dty26GA&_nc_ss=7b2a8&oh=00_AQBQb75sh5YCBzlk2IVtTaLe9BJyuXaFqU0qk6f2Wzp8zg&oe=6A610989"
              className="w-[50%] h-full bg-amber-300 rounded-2xl"
          ></img>
          <div className="w-[50%]">
              <div className="w-[90%]">Phạm Nhật Tân là ai?</div>
              <small className="text-white/50">Link</small>
              <div className="max-w-[100%] max-h-[80%] overflow-auto">
                  Tân là 1 trang trai đẳng cấp vô địch thiên hạ ,từ đó người ta
                  gọi là gs.ts thiên tài phạm nhật tân
              </div>
          </div>
      </div>
  );
}

export default CardTopRightBar