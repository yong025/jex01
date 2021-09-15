<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/header.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">Read Page</h1>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Dashboard v1</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->


    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <!-- left column -->
                <div class="col-md-12">
                    <!-- general form elements -->
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">Board Read</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                        <div class="card-body">
                            <div class="form-group">
                                <label for="exampleInputEmail10">BNO</label>
                                <input type="text" name="bno" class="form-control" id="exampleInputEmail10" placeholder="blank bno" value="<c:out value="${boardDTO.bno}"></c:out>" readonly>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Title</label>
                                <input type="text" name="title" class="form-control" id="exampleInputEmail1" placeholder="blank title" value="<c:out value="${boardDTO.title}"></c:out>" readonly>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail2">Writer</label>
                                <input type="text" name="writer" class="form-control" id="exampleInputEmail2" placeholder="blank writer" value="<c:out value="${boardDTO.writer}"></c:out>" readonly>
                            </div>
                            <div class="row">
                                <div class="col-sm-12"> <!-- 6은 절반 12는 전체 -->
                                    <!-- textarea -->
                                    <div class="form-group">
                                        <label>Textarea</label>
                                        <textarea name="content" class="form-control" rows="3" placeholder="Enter ..." disabled><c:out value="${boardDTO.content}"></c:out></textarea> <!-- textarea는 공백조심 -->
                                    </div>
                                </div>

                            </div>
                        </div>
                        <!-- /.card-body -->

                        <div class="card-footer">
                            <button type="button" class="btn btn-default btnList" style="float:left">목록</button>
                            <button type="button" class="btn btn-info btnMod" style="float:right">수정/삭제</button>
                        </div>


                        <div>
                            <c:forEach items="${boardDTO.files}" var="attach">
                                <div>
                                    <c:if test="${attach.image}">
                                       <img src="/viewFile?file=${attach.getThumbnail()}" onclick="javacript:showOrigin('${attach.getFileLink()}')">
                                    </c:if>
                                        ${attach.fileName}
                                </div>
                            </c:forEach>
                        </div>

                    </div>
                    <!-- /.card -->

                    <!-- replies -->
                    <div class="card direct-chat direct-chat-primary">
                        <div class="card-header">
                            <h3 class="card-title">Replies</h3>

                            <div class="card-tools">
                                <span title="3 New Messages" class="badge badge-primary addReplyBtn">Add Reply</span>
                                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                                <button type="button" class="btn btn-tool" title="Contacts" data-widget="chat-pane-toggle">
                                    <i class="fas fa-comments"></i>
                                </button>
                                <button type="button" class="btn btn-tool" data-card-widget="remove">
                                    <i class="fas fa-times"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.card-header -->
                        <div class="card-body">
                            <div class="direct-chat-messages">
                                <!-- /.replies -->


                            </div>
                            <!--/.replies -->
                        </div>
                     </div>
                </div>
            </div>
        </div>
    </section>


</div>

<form id="actionForm" action="/board/list" method="get">
    <input type="hidden" name="page" value="${pageRequestDTO.page}">
    <input type="hidden" name="size" value="${pageRequestDTO.size}">

    <!-- 검색조건 따라 붙게하는 코드 -->
    <c:if test="${pageRequestDTO.type != null}">
        <input type="hidden" name="type" value="${pageRequestDTO.type}">
        <input type="hidden" name="keyword" value="${pageRequestDTO.keyword}">
    </c:if>
</form>

<%-- modal start --%>
<div class="modal fade" id="modal-sm">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Reply</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="text" name="replyer">
                <input type="text" name="reply">

            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal">close</button>
                <button type="button" class="btn btn-primary operBtn">save change</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal exd-->

<!-- large modal -->
<div class="modal fade" id="modal-lg">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Modify/Remove</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="rno">
                <input type="text" name="replyerMod">
                <input type="text" name="replyMod">
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-info btnModReply">Modify</button>
                <button type="button" class="btn btn-danger btnRem">Remove</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- large modal -->
<div class="modal fade" id="modal-image">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
                <img id="targetImage">
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<%@include file="../includes/footer.jsp"%>

<script>



    const actionForm = document.querySelector("#actionForm");

    document.querySelector(".btnList").addEventListener("click" ,() => {
        actionForm.submit();
    }, false) //btnList에 page와 size를 물고오기 위해

    document.querySelector(".btnMod").addEventListener("click", () => {

        const bno = '${boardDTO.bno}';
        //수정하려면 bno가 필요함

        actionForm.setAttribute("action", "/board/modify")
        actionForm.innerHTML += `<input type='hidden' name='bno' value='\${bno}'>`
        actionForm.submit();
    })

</script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script> <!-- axios를 사용하기 위해서 추가 -->
<script src="/resources/js/reply.js"> //js 로딩 </script>

<script>

    const modalImage = new bootstrap.Modal(document.querySelector('#modal-image'))

    function showOrigin(fileLink){

        document.querySelector("#targetImage").src = `/viewFile?file=\${fileLink}`
        modalImage.show()

    }

    function after(result) {
        console.log("after..........");
        console.log(result)
    }
/*

    // reply.js를 로딩하면서 doA와 doB를 가져옴
    // doA만 부른다면 promise(약속어음) 형식으로 돌아온다.
    // promise를 다시 doA로 출력시키려면 then을 이용해야 함.(그 안에는 함수가 들어감)
    doA().then(result => console.log(result));

    doB(after)

    // axios로 보냈다면 json으로 data가 왔을 것임. java 객체로 보냈다고 하더라도.
    const reply = {bno:230, replyer:'user00',reply:'22222222'}

    doC(reply).then(result => console.log(result))

    // delete
    doD(112).then(result => console.log(result))

    // put(추가/수정)
    const reply = {rno:112, reply:"Update reply text...."}
    doE(reply).then(result => console.log(result))
*/

    //즉시 실행함수가 아닌 일반 함수로 정의한 이유 ? 댓글 추가 중 다른 사람이 댓글을 추가한 경우 다시 그 목록을 출력해야함
    // -> 추가할 때 마다 리스트를 가져오는게 좋으므로
    function getList() {
        const target = document.querySelector(".direct-chat-messages")
        const bno = '${boardDTO.bno}' //지금 해당 게시물의 번호
        function convertTemplate (replyObj) {

            console.log(replyObj)

            const {rno,bno,reply,replyer,replyDate,modDate}  = {...replyObj}

            const template = `<div class="direct-chat-msg">
                <div class="direct-chat-infos clearfix">
                    <span class="direct-chat-name float-left">\${rno} -- \${replyer}</span>
                    <span class="direct-chat-timestamp float-right">\${replyDate}</span>
                </div>
                <div class="direct-chat-text" data-rno='\${rno}' data-replyer='\${replyer}'>
                    \${reply}
                </div>
            </div>`
            return template
        }

        getReplyList(bno).then(data => {
            console.log(data) //array
            let str = "";
            data.forEach(reply => {
                str += convertTemplate(reply)
            })
            target.innerHTML = str
        })
    }

    //최초 실행 -> 즉시실행함수
    (function () {
        getList()
    })()

    //jQuery style
    //small modal를 변수로 저장
    const modalDiv = $('#modal-sm')

    let oper = null

    document.querySelector('.addReplyBtn').addEventListener("click", function () {

        oper = 'add' //댓글을 추가할꺼니까 oper를 add로 설정
        modalDiv.modal('show')

    },false)

    //버튼 누른 경우
    document.querySelector(".operBtn").addEventListener("click", function () {

        const bno = '${boardDTO.bno}'
        const replyer = document.querySelector("input[name='replyer']").value //jQuery
        const reply = document.querySelector("input[name='reply']").value

        if(oper === 'add') {
            const replyObj = {bno:bno, replyer:replyer, reply:reply} //키값 할당
            console.log(replyObj)
            addReply(replyObj).then(result => {
                getList() //갱신목적 함수
                modalDiv.modal('hide')
                document.querySelector("input[name='replyer']").value = ""
                document.querySelector("input[name='reply']").value = ""
            })
        }

    },false)
    //수정,삭제 DOM
    const modModal = $("#modal-lg")
    const modReplyer = document.querySelector("input[name='replyerMod']")
    const modReply = document.querySelector("input[name='replyMod']")
    const modRno = document.querySelector("input[name='rno']")

    document.querySelector(".direct-chat-messages").addEventListener("click", (e) => {

        const target = e.target
        const bno = '${boardDTO.bno}'
        if (target.matches(".direct-chat-text")) {
            const rno = target.getAttribute("data-rno")
            const replyer = target.getAttribute("data-replyer")
            const reply = target.innerHTML

            console.info(reply)

            console.log(rno, replyer, reply, bno)
            modRno.value = rno
            modReplyer.value = replyer
            modReply.value = reply

            modModal.modal('show')

            document.querySelector(".btnRem").setAttribute("data-rno", rno)

            modModal.modal('show')
        }
    },false)

    document.querySelector(".btnRem").addEventListener("click" ,(e) => {
        const rno = e.target.getAttribute("data-rno")

        removeReply(rno).then(result => {
            getList()

            modModal.modal('hide');
        })
    },false)

    document.querySelector(".btnModReply").addEventListener("click" , (e) => {
        const replyObj = {rno:modRno.value, reply:modReply.value}

        modifyReply(replyObj).then(result => {
            getList()

            modModal.modal("hide")
        })

    },false)




</script>
</body>
</html>

