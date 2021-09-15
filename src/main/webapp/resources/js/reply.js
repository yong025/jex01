/*
//get

//선언문 스타일
//return style
async function doA() { //await은 async가 필요

    console.log("doA...................")

    const response = await axios.get("/replies")
    const data = response.data
    console.log("doA..data" , data)

    return data

}

//선언식 스타일
//callback style
const doB = (fn) => {
    console.log("doB..............");
    try{
        axios.get('/replies').then(resoponse => {
            console.log(resoponse)
            const arr = resoponse.data
            //비동기 실행이 끝나면 함수를 호출하라
            fn(arr);
        })
    } catch(error) {
        console.log(err)
    }
}
//---------------------------------------------------------------


//post
async function doC(obj) {

    //비동기 처리화될것임
    const response = await axios.post("/replies",obj) //뒤에 obj는 파라미터
    //response.data 는 비동기 처리가 되기 때문에 언제 data가 올지 모른다 -> 그렇기 때문에 promise의 형식으로 온다.
    return response.data

}

const doD = async (rno) => {
    //비동기 처리화될것임
    const response = await axios.delete(`/replies/${rno}`) //뒤에 obj는 파라미터
    //response.data 는 비동기 처리가 되기 때문에 언제 data가 올지 모른다 -> 그렇기 때문에 promise의 형식으로 온다.
    return response.data
}

const doE = async (reply) => {

    const response = await axios.put(`/replies/${reply.rno}`, reply)
    return response.data
}

*/

const getReplyList = async (bno) => {

    const response = await axios.get(`/replies/list/${bno}`)
    return response.data
}

async function addReply(obj) {

    const response = await axios.post("/replies", obj)
    return response.data
} //return 방식으로 했기 때문에 다시 obj로 변환이 필요함

const removeReply = async (rno) => {
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}

const modifyReply = async (reply) => {
    const response = await axios.put(`/replies/${reply.rno}`, reply)
    return response.data
}