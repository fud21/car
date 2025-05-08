import os
import warnings
import logging
import easyocr

warnings.filterwarnings("ignore", category=UserWarning)
logging.getLogger("easyocr").setLevel(logging.ERROR)

def filter_plate_text(text):
    return ''.join(
        ch for ch in text
        if ch.isdigit() or '\uAC00' <= ch <= '\uD7A3'
    )

def main():
    path = input(
        "번호판 이미지 파일의 절대 경로를 입력해주세요: "
    ).strip()
    if not os.path.isfile(path):
        print("Error: 파일이 없습니다.")
        return

    reader = easyocr.Reader(['ko', 'en'], gpu=False)
    results = reader.readtext(path, detail=0)

    if results:
        raw_text = "".join(results)
        plate_text = filter_plate_text(raw_text)
        print("인식된 번호판:", plate_text)
    else:
        print("번호판 문자를 인식하지 못했습니다.")

if __name__ == '__main__':
    main()
