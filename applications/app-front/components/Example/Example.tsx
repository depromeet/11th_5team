import React from "react";
import Image from "next/image";
import SvgIcon from "public/svgs/vercel.svg";
import { ExampleWrap } from "./Example.styles";

const Example = () => {
  return (
    <>
      <ExampleWrap>
        안녕하세요
        <Image src={SvgIcon} alt="asd" width="283" height="64" />
      </ExampleWrap>
    </>
  );
};

export default Example;
