"use client";
import { useRouter } from "next/navigation";
import { PrimeReactProvider } from "primereact/api";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { InputText } from "primereact/inputtext";

export default function Login() {
  const router = useRouter();
  const onClickLogin = () => {
    router.push("system/dashboard");
  };
  return (
    <PrimeReactProvider>
      <div className="w-full grid justify-content-center">
        <Card className="col-5" style={{ transform: "translateY(30%)" }}>
          <div className="p-4">
            <div className="h-fit text-center bg-cyan-200">
              <label className="text-8xl font-bold">LOGIN SCREEN</label>
            </div>
            <div className="w-full flex justify-content-center mt-4">
              <div className="w-10 flex flex-column gap-2">
                <div className="w-full">
                  <label className="pb-2">Username</label>
                  <InputText className="w-full h-2rem"></InputText>
                </div>
                <div className="w-full">
                  <label className="pb-2">Password</label>
                  <InputText className="w-full h-2rem"></InputText>
                </div>
                <div className="w-full">
                  <Button
                    label="Login"
                    className="w-full h-2rem"
                    onClick={onClickLogin}
                  ></Button>
                </div>
              </div>
            </div>
          </div>
        </Card>
      </div>
    </PrimeReactProvider>
  );
}
